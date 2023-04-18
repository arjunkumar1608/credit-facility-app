package com.credit.facilty.loan.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CalculateLoanResponseDto {

    private BigDecimal interestRate;
    private BigDecimal totalInterest;
    private BigDecimal monthlyInstallmentAmount;
    private BigDecimal totalPayment;
    private BigDecimal annualCostRate;
    private BigDecimal allocationFee;

}
