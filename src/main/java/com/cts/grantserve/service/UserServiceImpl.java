package com.cts.grantserve.service;

import com.cts.grantserve.dto.UserDto;
import com.cts.grantserve.projection.IUserProjection;
import com.cts.grantserve.repository.IUserRepository;
import com.cts.grantserve.entity.User;
import com.cts.grantserve.exception.UserException;
import com.cts.grantserve.util.ClassUtilSeparator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserRepository userDAO;

    public String registerUser(UserDto userDto) throws UserException {
        User user = ClassUtilSeparator.userRegisterUtil(userDto);
        user.setStatus("Active");
        userDAO.save(user);
        return "Registered Successfully";
    }

    public IUserProjection fetchUser(Long userId) throws UserException{
        return userDAO.findByUserID(userId)
                .orElseThrow(() -> new UserException("User not found with ID: " + userId, HttpStatus.NOT_FOUND));
    }
    public String updateUser(Long userId,UserDto userDto) throws UserException {
        User existingUser = userDAO.findById(userId)
                .orElseThrow(() -> new UserException("Cannot update. User not found: " + userId,HttpStatus.NOT_FOUND));
        ClassUtilSeparator.userUpdateUtil(userDto, existingUser);
        userDAO.save(existingUser);
        return "Updated Successfully";
    }



}


