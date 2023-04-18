package com.credit.facility.loan.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class PayInstallmentResponseDto {

    private Long loanId;
    private BigDecimal paymentAmount;
    private LocalDate PaymentDate;
    private BigDecimal remainingPrincipal;
    private LocalDate dueDate;
}
