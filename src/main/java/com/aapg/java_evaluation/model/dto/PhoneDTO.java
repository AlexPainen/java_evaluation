package com.aapg.java_evaluation.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@Builder
public class PhoneDTO implements Serializable {

    private String number;
    private String cityCode;
    private String countryCode;
}
