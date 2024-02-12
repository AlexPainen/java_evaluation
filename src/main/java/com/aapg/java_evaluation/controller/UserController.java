package com.aapg.java_evaluation.controller;

import com.aapg.java_evaluation.model.dto.UserDTO;
import com.aapg.java_evaluation.model.entity.User;
import com.aapg.java_evaluation.model.payload.MessageResponse;
import com.aapg.java_evaluation.service.IUser;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private IUser userService;

    @PostMapping("user")
    public ResponseEntity<?> create(@RequestBody UserDTO userDTO){

        User userSave = null;
        Date date = new Date();

        try {
            userSave = userService.save(userDTO);
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Saved")
                    .object(UserDTO.builder()
                            .id(userSave.getId())
                            .name(userSave.getName())
                            .email(userSave.getEmail())
                            .password(userSave.getPassword())
                            .created(userSave.getCreated())
                            .modified(userSave.getModified())
                            .lasLogin(userSave.getLasLogin())
                            .jwt(userSave.getJwt())
                            .isActive(userSave.isActive())
                            .phones(userSave.getPhones())
                            .build())
                    .build()
            ,HttpStatus.CREATED);
        } catch (DataException dataException) {
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .message(dataException.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED
            );
        }
    }

    @PutMapping("user")
    public ResponseEntity<?> update(@RequestBody UserDTO userDTO){
        User userUpdate = null;
        Date date = new Date();

        try {
            if (userService.existsById(userDTO.getId())) {
                userUpdate = userService.save(userDTO);
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Updated")
                        .object(UserDTO.builder()
                                .id(userUpdate.getId())
                                .name(userUpdate.getName())
                                .email(userUpdate.getEmail())
                                .password(userUpdate.getPassword())
                                .created(userUpdate.getCreated())
                                .modified(userUpdate.getModified())
                                .lasLogin(userUpdate.getLasLogin())
                                .jwt(userUpdate.getJwt())
                                .isActive(userUpdate.isActive())
                                .phones(userUpdate.getPhones())
                                .build())
                        .build()
                        ,HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(
                        MessageResponse.builder()
                                .message("Duplicated User id")
                                .build()
                        ,HttpStatus.BAD_REQUEST
                );
            }
        } catch (DataException dataException) {
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .message(dataException.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED
            );
        }
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id){

        try {
            User user = userService.findById(id);
            userService.delete(user);
            return new ResponseEntity<>(user, HttpStatus.NO_CONTENT);
        } catch (DataException dataException) {
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .message(dataException.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.METHOD_NOT_ALLOWED
            );
        }
    }

    @GetMapping("user/{id}")
    public ResponseEntity<?> showById(@PathVariable UUID uuid){

        User user = userService.findById(uuid);

        try {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("")
                    .object(UserDTO.builder()
                            .id(user.getId())
                            .name(user.getName())
                            .email(user.getEmail())
                            .password(user.getPassword())
                            .created(user.getCreated())
                            .modified(user.getModified())
                            .lasLogin(user.getLasLogin())
                            .jwt(user.getJwt())
                            .isActive(user.isActive())
                            .phones(user.getPhones())
                            .build())
                    .build()
            , HttpStatus.OK);
        } catch (DataException dataException) {
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .message(dataException.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.NOT_FOUND
            );
        }
    }
}
