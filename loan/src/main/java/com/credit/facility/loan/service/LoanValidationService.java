package com.credit.facility.loan.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.credit.facility.loan.customer.service.CustomerEntityService;
import com.credit.facility.loan.dto.ApplyLoanDto;
import com.credit.facility.loan.entity.LoanEntity;
import com.credit.facility.loan.enums.ErrorMessage;
import com.credit.facility.loan.enums.LoanStatusType;
import com.credit.facility.loan.util.exception.IllegalFieldException;
import com.credit.facility.loan.util.exception.ItemNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LoanValidationService {

    private final CustomerEntityService cusCustomerEntityService;

    public void controlIsParameterNotNull(Integer installmentCount, BigDecimal principalLoanAmount) {

        boolean hasNull = installmentCount == null || principalLoanAmount == null;

        if(hasNull){
            throw new IllegalFieldException(ErrorMessage.PARAMETER_CANNOT_BE_NULL);
        }
    }

    public void controlIsParameterNotNull(ApplyLoanDto loaApplyLoanDto) {

        boolean hasNull = loaApplyLoanDto.getInstallmentCount() == null     ||
                loaApplyLoanDto.getPrincipalLoanAmount() == null  ||
                loaApplyLoanDto.getMonthlySalary() == null;

        if(hasNull){
            throw new IllegalFieldException(ErrorMessage.PARAMETER_CANNOT_BE_NULL);
        }
    }

    public void controlIsInterestRateNotNegative(BigDecimal interestRate) {

        if(interestRate.compareTo(BigDecimal.ZERO)<0){
            throw new IllegalFieldException(ErrorMessage.INTEREST_RATE_CANNOT_BE_NEGATIVE);
        }
    }


    public void controlIsInstallmentAmountPositive(BigDecimal monthlyInstallmentAmount) {

        if(monthlyInstallmentAmount.compareTo(BigDecimal.ZERO)<=0){
            throw new IllegalFieldException(ErrorMessage.INSTALLMENT_AMOUNT_MUST_BE_POSITIVE);
        }
    }

    public void controlIsTotalPaymentPositive(BigDecimal totalPayment) {

        if(totalPayment.compareTo(BigDecimal.ZERO)<=0){
            throw new IllegalFieldException(ErrorMessage.TOTAL_PAYMENT_MUST_BE_POSITIVE);
        }
    }

    public void controlIsLateFeeRateNotNegative(BigDecimal lateFeeRate) {

        if(lateFeeRate.compareTo(BigDecimal.ZERO)<0){
            throw new IllegalFieldException(ErrorMessage.LATE_FEE_RATE_CANNOT_BE_NEGATIVE);
        }
    }

    public void controlIsTotalLateFeePositive(BigDecimal totalLateFee) {

        if(totalLateFee.compareTo(BigDecimal.ZERO)<=0){
            throw new IllegalFieldException(ErrorMessage.TOTAL_LATE_FEE_MUST_BE_POSITIVE);
        }
    }

    public void controlIsLateInterestTaxNotNegative(BigDecimal lateInterestTax) {

        if(lateInterestTax.compareTo(BigDecimal.ZERO)<0){
            throw new IllegalFieldException(ErrorMessage.LATE_INTEREST_TAX_CANNOT_BE_NEGATIVE);
        }
    }

    public void controlIsLoanAmountNotGreaterThanMaxLoanAmount(BigDecimal principalLoanAmount, BigDecimal maxLoanAmount) {

        if(principalLoanAmount.compareTo(maxLoanAmount)>0){

            ErrorMessage loaErrorMessage = ErrorMessage.LOAN_AMOUNT_CANNOT_BE_GREATER_THAN_MAX_AMOUNT;
            loaErrorMessage.setDetailMessage(String.valueOf(maxLoanAmount));

            throw new IllegalFieldException(loaErrorMessage);
        }
    }

    public void controlIsCustomerExist(Long customerId) {

        boolean isExist = cusCustomerEntityService.existsById(customerId);

        if (!isExist){

            throw new ItemNotFoundException(ErrorMessage.CUSTOMER_NOT_FOUND);
        }
    }

    public void controlIsMonthlyInstallmentAmountPositive(BigDecimal monthlyInstallmentAmount) {

        if(monthlyInstallmentAmount.compareTo(BigDecimal.ZERO)<=0){
            throw new IllegalFieldException(ErrorMessage.MONTHLY_INSTALLMENT_AMOUNT_MUST_BE_POSITIVE);
        }
    }

    public void controlIsInterestAmountNotNegative(BigDecimal interestAmount) {

        if(interestAmount.compareTo(BigDecimal.ZERO)<0){
            throw new IllegalFieldException(ErrorMessage.INTEREST_AMOUNT_CANNOT_BE_NEGATIVE);
        }
    }

    public void controlIsPrincipalLoanAmountPositive(BigDecimal principalLoanAmount) {

        if(principalLoanAmount.compareTo(BigDecimal.ZERO)<=0){
            throw new IllegalFieldException(ErrorMessage.PRINCIPAL_lOAN_AMOUNT_MUST_BE_POSITIVE);
        }
    }

    public Long controlIsLoanDueDatePast(LocalDate dueDate) {

        LocalDate now = LocalDate.now();
        long lateDayCount = ChronoUnit.DAYS.between(dueDate, now);

        if(lateDayCount < 1 ){

            ErrorMessage loaErrorMessage = ErrorMessage.DUE_DATE_HAS_NOT_PASSED_YET;
            loaErrorMessage.setDetailMessage(String.valueOf(dueDate));

            throw new IllegalFieldException(loaErrorMessage);
        }
        return lateDayCount;
    }

    public void controlIsRemainingPrincipalNotNegative(BigDecimal remainingPrincipal) {
        if(remainingPrincipal.compareTo(BigDecimal.ZERO)<0){
            throw new IllegalFieldException(ErrorMessage.REMAINING_PRINCIPAL_MUST_BE_POSITIVE);
        }
    }

    public void controlIsTaxRateNotNegative(BigDecimal taxRate) {
        if(taxRate.compareTo(BigDecimal.ZERO)<0){
            throw new IllegalFieldException(ErrorMessage.TAX_RATE_CANNOT_BE_NEGATIVE);
        }
    }

    public void controlIsInstallmentCountNotGreaterThanInstallmentCountLimit(int installmentCount, int installmentCountLimit) {
        if(installmentCount>installmentCountLimit){

            ErrorMessage loaErrorMessage = ErrorMessage.INSTALLMENT_COUNT_CANNOT_BE_LARGER_THAN_LIMIT;
            loaErrorMessage.setDetailMessage(String.valueOf(installmentCountLimit));

            throw new IllegalFieldException(loaErrorMessage);
        }
    }

    public void controlIsLoanNotAlreadyPaidOff(LoanEntity loaLoan) {
        if(loaLoan.getLoanStatusType() == LoanStatusType.PAID){
            throw new IllegalFieldException(ErrorMessage.LOAN_ALREADY_PAID_OFF);
        }
    }
}
