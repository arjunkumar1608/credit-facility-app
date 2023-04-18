package com.credit.facility.loan.dto;

import java.math.BigDecimal;

import com.credit.facility.loan.enums.LoanStatusType;

import lombok.Data;

@Data
public class PayLoanResponseDto {
	private Long id;
	private Long customerId;
	private BigDecimal paidAmount;
	private BigDecimal remainingAmount;
	private LoanStatusType loanStatusType;
}
