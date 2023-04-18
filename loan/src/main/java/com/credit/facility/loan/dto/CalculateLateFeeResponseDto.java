package com.credit.facility.loan.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CalculateLateFeeResponseDto {

    private BigDecimal totalLateFee;
    private BigDecimal lateFeeRate;
    private BigDecimal lateInterestTax;
    private Long lateDayCount;


}
