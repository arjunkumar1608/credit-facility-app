package com.credit.facilty.loan.customer.dto;

import lombok.Data;

@Data
public class CustomerSaveDto {

	private String name;
	private String surname;
	private Long identity;
	private String password;
}
