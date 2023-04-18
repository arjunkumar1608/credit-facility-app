package com.credit.facility.loan.security.dto;

import lombok.Data;

@Data
public class LoginRequestDto {

    private Long identity;
    private String password;
}
