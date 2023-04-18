package com.credit.facility.loan.customer.dto;

import lombok.Data;

@Data
public class CustomerDto {

    Long id;
    private String name;
    private String surname;
    private Long identityNo;
}
