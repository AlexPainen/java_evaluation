package com.aapg.java_evaluation.model.dao;

import com.aapg.java_evaluation.model.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PhoneDAO extends CrudRepository<User, UUID> {
}
