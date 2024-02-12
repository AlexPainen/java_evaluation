package com.aapg.java_evaluation.model.dto;

import com.aapg.java_evaluation.model.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@Data
@ToString
@Builder
public class PhoneDTO implements Serializable {

    private UUID id;
    private String number;
    private String cityCode;
    private String countryCode;
    private User user;
}
