package com.iAnalyze.AuthMicroService.service;

import com.iAnalyze.AuthMicroService.dao.UserDao;
import com.iAnalyze.AuthMicroService.exceptions.CustomRuntimeException;
import com.iAnalyze.AuthMicroService.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public User validateUser(String email, String password) throws CustomRuntimeException {
        if(email != null) email = email.toLowerCase();
        return userDao.findByEmailAndPassword(email, password);
    }


    @Override
    public User registerUser(UUID id, User user) throws CustomRuntimeException {
        UUID newUserId = Optional.ofNullable(id)
                .orElse(UUID.randomUUID());
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        String email = user.getEmail();
        if (email != null) email = email.toLowerCase();
        if(!pattern.matcher(email).matches())
            throw new CustomRuntimeException("Invalid email format");

        Integer count = userDao.getCountByEmail(email);
        if(count > 0)
            throw new CustomRuntimeException("Email already in use");

        userDao.registerUser(newUserId, user);
        return user;
    }
}
