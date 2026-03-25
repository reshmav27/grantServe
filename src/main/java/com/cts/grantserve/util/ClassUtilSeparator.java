package com.cts.grantserve.util;

import com.cts.grantserve.dto.*;
import com.cts.grantserve.entity.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ClassUtilSeparator {

    public static Researcher researcherRegisterUtil(ResearcherDto dto, Researcher existingResearcher) {
        existingResearcher.setDob(dto.dob());
        existingResearcher.setGender(dto.gender());
        existingResearcher.setInstitution(dto.institution());
        existingResearcher.setDepartment(dto.department());
        return existingResearcher;
    }

    public static ResearcherDocument documentUploadUtil(ResearcherDocumentDto dto) {
        ResearcherDocument doc = new ResearcherDocument();
        doc.setDocType(dto.docType());
        doc.setFileURI(dto.fileURI());

        // Link the researcher using the ID from the DTO
        Researcher researcher = new Researcher();
        researcher.setResearcherID(dto.researcherID());
        doc.setResearcher(researcher);

        return doc;
    }

    public static Proposal proposalUtil(ProposalDto proposalDto){
        Proposal proposal =new Proposal();
        proposal.setFileURI(proposalDto.fileURI());
        proposal.setSubmittedDate(LocalDateTime.now());
        proposal.setStatus("Submitted");
        return  proposal;
    }
    public static User userUpdateUtil(UserDto userDto, User user){
        user.setEmail(userDto.email());
        user.setName(userDto.name());
        user.setPhone(userDto.phone());
        user.setEmail(userDto.email());
        return  user;
    }


    public static User userRegisterUtil(UserDto userDto){

        User newUser = new User();
        newUser.setName(userDto.name());
        newUser.setEmail(userDto.email());
        newUser.setPhone(userDto.phone());
        newUser.setRole(userDto.role());

        if ("RESEARCHER".equalsIgnoreCase(userDto.role())) {
            Researcher researcher = new Researcher();
            researcher.setName(userDto.name());
            researcher.setStatus("PENDING");
            researcher.setContactInfo("Email : "+userDto.email()+" ,Phone : "+userDto.phone());
            newUser.setResearcher(researcher);
            researcher.setUser(newUser);
        }

        return newUser;
    }

    public static Disbursement DisbursementUtil(DisbursementDto disbursementDto){
        Disbursement disbursement = new Disbursement();
        disbursement.setAmount(disbursementDto.amount());
        disbursement.setDate(LocalDate.now());
        disbursement.setStatus("PENDING");
        return disbursement;
    }
    public static Payment PaymentUtil(PaymentDto paymentDto){
        Payment payment=new Payment();
        payment.setMethod(paymentDto.method());
        payment.setStatus("SUCCESS");
        payment.setDate(LocalDate.now());
        return payment;
    }

    public static Program programUtil(ProgramDto programDto) {
        Program program = new Program();

        program.setProgramID(programDto.programID());
        program.setTitle(programDto.title());
        program.setDescription(programDto.description());
        program.setBudget(programDto.budget());
        program.setStartDate(programDto.startDate());
        program.setEndDate(programDto.endDate());
        program.setStatus(programDto.status());

        return program;
    }

    public static Budget budgetUtil(BudgetDto budgetDto) {
        Budget budget = new Budget();

        budget.setBudgetID(budgetDto.budgetID());
        budget.setStatus(budgetDto.status());
        budget.setSpentAmount(budgetDto.spentAmount());
        budget.setAllocatedAmount(budgetDto.allocatedAmount());
        budget.setRemainingAmount(budgetDto.remainingAmount());

        return budget;
    }

}
