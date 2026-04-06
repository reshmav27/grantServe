package com.cts.grantserve.service;

import com.cts.grantserve.dto.DisbursementDto;
import com.cts.grantserve.entity.Allocation;
import com.cts.grantserve.entity.Budget;
import com.cts.grantserve.entity.Disbursement;
import com.cts.grantserve.entity.GrantApplication;
import com.cts.grantserve.exception.DisbursementException;
import com.cts.grantserve.repository.AllocationRepository;
import com.cts.grantserve.repository.BudgetRepository;
import com.cts.grantserve.repository.DisbursementRepository;
import com.cts.grantserve.repository.IGrantApplicationRepository;
import com.cts.grantserve.util.ClassUtilSeparator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class DisbursementServiceImpl implements IDisbursementService {

    @Autowired
    private DisbursementRepository disbursementRepo;

    @Autowired
    private AllocationRepository allocationRepo;

    @Autowired
    private IGrantApplicationRepository applicationRepo;

    @Override
    @Transactional
    public Disbursement initiateDisbursement(DisbursementDto dto) {
        log.info("Service: Initiating disbursement for Application ID: {} Amount: {}",
                dto.applicationID(), dto.amount());

        // 1. Fetch Allocation (The researcher's specific "wallet")
        Allocation allocation = allocationRepo.findByApplicationApplicationID(dto.applicationID())
                .orElseThrow(() -> {
                    return new DisbursementException("Funds have not been allocated yet. Allocation must be initiated first.", HttpStatus.BAD_REQUEST);
                });

        // 2. Validate against the Researcher's Remaining Balance
        if (dto.amount() > allocation.getRemainingBalance()) {
            throw new DisbursementException("Insufficient allocated funds. Remaining balance: " + allocation.getRemainingBalance(), HttpStatus.BAD_REQUEST);
        }

        // 3. Update Allocation Financials
        Double newDisbursedTotal = (allocation.getDisbursedAmount() != null ? allocation.getDisbursedAmount() : 0.0) + dto.amount();
        allocation.setDisbursedAmount(newDisbursedTotal);
        allocation.setRemainingBalance(allocation.getTotalAwardedAmount() - newDisbursedTotal);

        if (allocation.getRemainingBalance() <= 0) {
            allocation.setStatus("EXHAUSTED");
        }
        allocationRepo.save(allocation);

        // 4. Map DTO to Entity using your Util
        Disbursement disbursement = ClassUtilSeparator.DisbursementUtil(dto);

        // 5. Link Application to Disbursement
        GrantApplication app = applicationRepo.findById(dto.applicationID())
                .orElseThrow(() -> new DisbursementException("Application not found", HttpStatus.NOT_FOUND));

        disbursement.setApplication(app);


        Disbursement saved = disbursementRepo.save(disbursement);
        log.info("Service Success: Disbursement ID {} created. New Balance: {}",
                saved.getDisbursementID(), allocation.getRemainingBalance());

        return saved;
    }


    @Override
    public List<Disbursement> trackByApplication(Long appId) {
        log.info("Fetching disbursements for Application ID: {}", appId);
        List<Disbursement> results = disbursementRepo.findByApplication_ApplicationID(appId);
        log.info("Found {} records for Application ID: {}", results.size(), appId);
        return results;
    }

    @Override
    public List<Disbursement> trackByStatus(String status) {
        log.info("Filtering disbursements by status: {}", status);
        return disbursementRepo.findByStatus(status);
    }

    @Override
    @Transactional
    public void deleteDisbursement(Long id) {
        log.info("Attempting to delete disbursement record ID: {}", id);

        if (!disbursementRepo.existsById(id)) {
            log.error("Delete Failed: Disbursement ID {} does not exist", id);
            throw new DisbursementException("Cannot delete: Disbursement ID not found", HttpStatus.NOT_FOUND);
        }

        disbursementRepo.deleteById(id);
        log.info("Successfully deleted disbursement record ID: {}", id);
    }
}