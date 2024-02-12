package com.aapg.java_evaluation.service.impl;

import com.aapg.java_evaluation.model.dao.UserDAO;
import com.aapg.java_evaluation.model.dto.UserDTO;
import com.aapg.java_evaluation.model.entity.User;
import com.aapg.java_evaluation.service.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
public class UserImpl implements IUser {

    @Autowired
    private UserDAO userDAO;

    @Transactional
    @Override
    public User save(UserDTO userDTO) {

        Date date = new Date();

        User user = User.builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .created(date)
                .modified(date)
                .lasLogin(null)
                .jwt(null)
                .isActive(false)
                .phones(userDTO.getPhones())
                .build();

        return userDAO.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User findById(UUID id) {
        return userDAO.findById(id).orElse(null);
    }

    @Override
    public boolean existsById(UUID id) {
        return userDAO.existsById(id);
    }

    @Transactional
    @Override
    public void delete(User user) {
        userDAO.delete(user);
    }
}
