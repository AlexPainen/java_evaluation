package com.aapg.java_evaluation.controller;

import com.aapg.java_evaluation.model.dto.PhoneDTO;
import com.aapg.java_evaluation.model.dto.UserDTO;
import com.aapg.java_evaluation.model.entity.Phone;
import com.aapg.java_evaluation.model.entity.User;
import com.aapg.java_evaluation.model.payload.MessageResponse;
import com.aapg.java_evaluation.service.IUser;
import com.aapg.java_evaluation.util.Config;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private IUser userService;
    @Autowired
    private Config config;

    @PostMapping(value = "user",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody UserDTO userDTO) {
        try {
            if (userService.existsByEmail(userDTO.getEmail())) {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("The email entered already exists")
                        .build()
                        , HttpStatus.CONFLICT);
            }
            if (isValidEmail(userDTO.getEmail())) {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("The email entered does not meet the necessary parameters")
                        .build()
                        , HttpStatus.CONFLICT);
            }
            if (isValidPassword(userDTO.getPassword())) {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("The password entered does not meet the necessary parameters")
                        .build()
                        , HttpStatus.CONFLICT);
            } else {
                User user = userService.save((User.builder()
                        .name(userDTO.getName())
                        .password(userDTO.getPassword())
                        .email(userDTO.getEmail())
                        .phones(phoneDTOToPhone(userDTO.getPhones())))
                        .build());
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("User saved")
                        .object(UserDTO.builder()
                                .id(user.getId())
                                .name(user.getName())
                                .email(user.getEmail())
                                .password(user.getPassword())
                                .phones(phoneToPhoneDTO(user.getPhones()))
                                .build())
                        .build()
                        , HttpStatus.CREATED
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

    @PutMapping(value = "user",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody UserDTO userDTO) {

        try {
            if (userDTO.getId() == null) {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("User id parameter is not found")
                        .build()
                        , HttpStatus.CONFLICT);
            }
            if (isValidEmail(userDTO.getEmail())) {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("The email entered does not meet the necessary parameters")
                        .build()
                        , HttpStatus.CONFLICT);
            }
            if (isValidPassword(userDTO.getPassword())) {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("The password entered does not meet the necessary parameters")
                        .build()
                        , HttpStatus.CONFLICT);
            } else {
                User userSave = userService.save((User.builder()
                        .id(userDTO.getId())
                        .name(userDTO.getName())
                        .password(userDTO.getPassword())
                        .email(userDTO.getEmail())
                        .phones(phoneDTOToPhone(userDTO.getPhones())))
                        .build());
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("User updated")
                        .object(UserDTO.builder()
                                .id(userSave.getId())
                                .name(userSave.getName())
                                .email(userSave.getEmail())
                                .password(userSave.getPassword())
                                .phones(phoneToPhoneDTO(userSave.getPhones()))
                                .build())
                        .build()
                        , HttpStatus.CREATED
                );
            }
        } catch (DataException dataException) {
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .message(dataException.getMessage())
                            .object(null)
                            .build()
                    , HttpStatus.BAD_REQUEST
            );
        }
    }

    @DeleteMapping(value = "user/{uuid}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable String uuid) {
        User user = userService.findById(UUID.fromString(uuid));
        try {
            if (user == null) {
                return new ResponseEntity<>(
                        MessageResponse.builder()
                                .message("User not found")
                                .build()
                        , HttpStatus.METHOD_NOT_ALLOWED);
            } else {
                userService.delete(user);
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("User deleted")
                        .build()
                        , HttpStatus.OK);
            }
        } catch (DataException dataException) {
            return new ResponseEntity<>(
                    MessageResponse.builder()
                            .message(dataException.getMessage())
                            .build()
                    , HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping(value = "user/{uuid}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> showById(@PathVariable String uuid) {
        try {
            User user;
            if (userService.existsById(UUID.fromString(uuid))) {
                user = userService.findById(UUID.fromString(uuid));
            } else {
                return new ResponseEntity<>(
                        MessageResponse.builder()
                                .message("User not found")
                                .object(null)
                                .build()
                        , HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(MessageResponse.builder()
                    .object(UserDTO.builder()
                            .id(user.getId())
                            .name(user.getName())
                            .email(user.getEmail())
                            .password(user.getPassword())
                            .phones(phoneToPhoneDTO(user.getPhones()))
                            .build())
                    .build()
                    , HttpStatus.OK
            );
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

    @GetMapping(value = "users",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> showAll() {
        try {
            List<User> users = userService.findAll();

            if (users == null) {
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("No users found")
                        .object(null)
                        .build()
                        , HttpStatus.NOT_FOUND);
            } else {
                List<UserDTO> userDTOS = new ArrayList<>();
                for (User user : users) {
                    userDTOS.add(UserDTO.builder()
                            .id(user.getId())
                            .name(user.getName())
                            .email(user.getEmail())
                            .password(user.getPassword())
                            .phones(phoneToPhoneDTO(user.getPhones()))
                            .build());
                }

                return new ResponseEntity<>(MessageResponse.builder()
                        .object(userDTOS)
                        .build()
                        , HttpStatus.OK);
            }
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

    private boolean isValidEmail(String email) {
        String regx = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(email);
        return !matcher.matches();
    }

    private boolean isValidPassword(String email) {
        String regx = config.getPasswordExpression();
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(email);
        return !matcher.matches();
    }

    private List<PhoneDTO> phoneToPhoneDTO(List<Phone> phones) {
        List<PhoneDTO> output = new ArrayList<>();

        for (Phone phone : phones) {
            output.add(PhoneDTO.builder()
                    .number(phone.getNumber())
                    .cityCode(phone.getCityCode())
                    .countryCode(phone.getCountryCode())
                    .build());
        }
        return output;
    }

    private List<Phone> phoneDTOToPhone(List<PhoneDTO> phones) {
        List<Phone> output = new ArrayList<>();

        for (PhoneDTO phone : phones) {
            output.add(Phone.builder()
                    .number(phone.getNumber())
                    .cityCode(phone.getCityCode())
                    .countryCode(phone.getCountryCode())
                    .build());
        }
        return output;
    }
}
