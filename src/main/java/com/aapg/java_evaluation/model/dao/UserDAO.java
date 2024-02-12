package com.aapg.java_evaluation.model.dao;

import com.aapg.java_evaluation.model.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserDAO extends CrudRepository<User, UUID> {
    boolean existsByEmail(String email);
}
