package com.cts.grantserve.util;

import com.cts.grantserve.dto.DisbursementDto;
import com.cts.grantserve.dto.ProposalDto;
import com.cts.grantserve.dto.ResearcherDocumentDto;
import com.cts.grantserve.dto.ResearcherDto;
import com.cts.grantserve.dto.UserDto;
import com.cts.grantserve.entity.Disbursement;
import com.cts.grantserve.entity.Proposal;
import com.cts.grantserve.entity.Researcher;
import com.cts.grantserve.entity.ResearcherDocument;
import com.cts.grantserve.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ClassUtilSeparator {

    public static Researcher researcherRegisterUtil(ResearcherDto dto) {
        Researcher researcher = new Researcher();
        // Record components are accessed via name() method
        researcher.setName(dto.name());
        researcher.setDob(dto.dob());
        researcher.setGender(dto.gender());
        researcher.setInstitution(dto.institution());
        researcher.setDepartment(dto.department());
        researcher.setContactInfo(dto.contactInfo());
        return researcher;
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



        return newUser;

    }

    public static Disbursement DisbursementUtil(DisbursementDto disbursementDto){
        Disbursement disbursement = new Disbursement();
        disbursement.setAmount(disbursementDto.amount());
        disbursement.setDate(LocalDate.now());
        disbursement.setStatus("PENDING");
        return disbursement;
    }


}
