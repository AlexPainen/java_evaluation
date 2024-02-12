package com.aapg.java_evaluation.service;

import com.aapg.java_evaluation.model.dto.UserDTO;
import com.aapg.java_evaluation.model.entity.User;

import java.util.UUID;

public interface IUser {
    User save(UserDTO user);
    User findById(UUID id);
    boolean existsById(UUID id);
    boolean existsByEmail(String email);
    void delete(User user);
}
