package com.aapg.java_evaluation.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
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
    private List<PhoneDTO> phones;
}
