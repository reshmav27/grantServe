package com.cts.grantserve.util;

import com.cts.grantserve.dto.ProposalDto;
import com.cts.grantserve.dto.UserDto;
import com.cts.grantserve.entity.Proposal;
import com.cts.grantserve.entity.User;
import lombok.Data;

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
}
