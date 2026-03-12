package com.cts.grantserve.service;

import com.cts.grantserve.dao.IUserDAO;
import com.cts.grantserve.entity.User;
import com.cts.grantserve.exception.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private IUserDAO userDAO;

    public String loginValidation(Long userID, String password)throws UserException {
        User user = userDAO.findById(userID)
                           .orElseThrow(() -> new RuntimeException("User not found"));;
        if(userID.equals(user.getUserID())&&password.equals(user.getPassword()))
            return user.getRole()+" Login Successful";
        else
            return "Login Failed";
    }

    public String registerUser(User user) throws UserException{
        userDAO.save(user);
        return "Registered Successfully";

    }
}
