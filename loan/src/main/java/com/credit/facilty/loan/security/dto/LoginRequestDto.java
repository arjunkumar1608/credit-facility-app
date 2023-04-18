package com.credit.facilty.loan.security.dto;

import lombok.Data;

@Data
public class LoginRequestDto {

    private Long identity;
    private String password;
}
