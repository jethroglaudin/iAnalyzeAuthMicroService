package com.iAnalyze.AuthMicroService.service;

import com.iAnalyze.AuthMicroService.models.User;

import java.util.UUID;

public interface UserService {

    User validateUser(String email, String password);

//    User registerUser(UUID id, String username, String email, String password);
    User registerUser(UUID id, User user);
}
