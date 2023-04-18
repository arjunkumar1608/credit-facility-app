package com.credit.facilty.loan.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ApplyLoanDto {

	private Integer installmentCount;
	private BigDecimal principalLoanAmount;
	private BigDecimal monthlySalary;
}
