package com.aapg.java_evaluation.service.impl;

import com.aapg.java_evaluation.model.dao.UserDAO;
import com.aapg.java_evaluation.model.entity.User;
import com.aapg.java_evaluation.service.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserImpl implements IUser {

    @Autowired
    private UserDAO userDAO;

    @Transactional
    @Override
    public User save(User user) {
        Date date = new Date();

        return userDAO.save(User.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .created(date)
                .modified(date)
                .lasLogin(null)
                .token(null)
                .isActive(false)
                .phones(user.getPhones())
                .build());
    }

    @Transactional(readOnly = true)
    @Override
    public User findById(UUID id) {
        return userDAO.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return (List<User>) userDAO.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsById(UUID id) {
        return userDAO.existsById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByEmail(String email) {
        return userDAO.existsByEmail(email);
    }

    @Transactional
    @Override
    public void delete(User user) {
        userDAO.delete(user);
    }
}
