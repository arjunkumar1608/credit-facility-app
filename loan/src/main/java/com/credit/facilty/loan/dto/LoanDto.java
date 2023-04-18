package com.credit.facilty.loan.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.credit.facilty.loan.enums.LoanStatusType;

import lombok.Data;

@Data
public class LoanDto {

    private Long id;
    private Long customerId;
    private Integer installmentCount;
    private BigDecimal principalLoanAmount;
    private BigDecimal monthlyInstallmentAmount;
    private BigDecimal interestToBePaid;
    private BigDecimal principalToBePaid;
    private BigDecimal remainingPrincipal;
    private LocalDate dueDate;
    private LoanStatusType loanStatusType;

}
