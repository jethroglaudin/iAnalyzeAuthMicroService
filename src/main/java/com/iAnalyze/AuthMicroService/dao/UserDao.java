package com.iAnalyze.AuthMicroService.dao;

import com.iAnalyze.AuthMicroService.models.User;

import java.util.UUID;

public interface UserDao {
    Integer registerUser(UUID id, User user);

    User findByEmailAndPassword(String email, String password);

    Integer getCountByEmail(String email);

    User findByEmail(String email);
}
