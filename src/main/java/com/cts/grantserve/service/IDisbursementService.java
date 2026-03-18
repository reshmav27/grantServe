package com.cts.grantserve.service;

import com.cts.grantserve.dto.DisbursementDto;
import com.cts.grantserve.entity.Disbursement;
import java.util.List;

public interface IDisbursementService {
    Disbursement initiateDisbursement(DisbursementDto dto);
    void reconcileBudget(Long programId, Double amount);
    List<Disbursement> trackByApplication(Long appId);
    List<Disbursement> trackByStatus(String status);
    void deleteDisbursement(Long id);
}