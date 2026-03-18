package com.cts.grantserve.util;

import com.cts.grantserve.dto.DisbursementDto;
import com.cts.grantserve.dto.ProposalDto;
import com.cts.grantserve.dto.UserDto;
import com.cts.grantserve.entity.Disbursement;
import com.cts.grantserve.entity.Proposal;
import com.cts.grantserve.entity.User;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ClassUtilSeparator {
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
        newUser.setPassword(userDto.password());

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
