package com.aapg.java_evaluation.model.dto;

import com.aapg.java_evaluation.model.entity.Phone;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@ToString
@Builder
public class UserDTO implements Serializable {

    private UUID id;
    private String name;
    private String email;
    private String password;
    private Date created;
    private Date modified;
    private Date lasLogin;
    private String jwt;
    private boolean isActive;
    private List<Phone> phones;
}
