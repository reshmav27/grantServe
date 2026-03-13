package com.cts.grantserve.service;

import com.cts.grantserve.dto.UserDto;
import com.cts.grantserve.Repository.IUserRepository;
import com.cts.grantserve.entity.User;
import com.cts.grantserve.exception.UserException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService{
    @Autowired
    private IUserRepository userDAO;

    public String registerUser(UserDto userDto) throws UserException{
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        userDAO.save(user);
        return "Registered Successfully";

    }
}
