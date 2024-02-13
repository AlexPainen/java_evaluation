package com.aapg.java_evaluation.service;

import com.aapg.java_evaluation.model.entity.User;

import java.util.List;
import java.util.UUID;

public interface IUser {
    User save(User user);
    User findById(UUID id);
    List<User> findAll();
    boolean existsById(UUID id);
    boolean existsByEmail(String email);
    void delete(User user);
}
