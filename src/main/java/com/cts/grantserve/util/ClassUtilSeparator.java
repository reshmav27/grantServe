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
        proposal.setFileURI(proposalDto.getFileURI());
        proposal.setSubmittedDate(LocalDateTime.now());
        proposal.setStatus("Submitted");
        return  proposal;
    }
    public static User userUpdateUtil(UserDto userDto, User user){
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setPhone(userDto.getPhone());
        user.setEmail(userDto.getEmail());
        return  user;
    }

    public static User userRegisterUtil(UserDto userDto){
        User newUser = new User();
        newUser.setName(userDto.getName());
        newUser.setEmail(userDto.getEmail());
        newUser.setPhone(userDto.getPhone());
        newUser.setRole(userDto.getRole());
        newUser.setPassword(userDto.getPassword());

        return newUser;

    }
}
