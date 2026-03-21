package com.cts.grantserve.service;

import com.cts.grantserve.dto.UserDto;
import com.cts.grantserve.entity.User;
import com.cts.grantserve.exception.UserException;
import com.cts.grantserve.projection.IUserProjection;

import java.util.List;

public interface IUserService {

    public String registerUser(UserDto user) throws UserException;

    IUserProjection fetchUser(Long userId);

    public String updateUser(Long userId,UserDto userDto) throws UserException;

    String UserLoginValidation(User user);
}
