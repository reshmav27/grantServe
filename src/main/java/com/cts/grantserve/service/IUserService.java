package com.cts.grantserve.service;

import com.cts.grantserve.DTO.UserDto;
import com.cts.grantserve.entity.User;
import com.cts.grantserve.exception.UserException;

public interface IUserService {

    public String registerUser(UserDto user) throws UserException;
}
