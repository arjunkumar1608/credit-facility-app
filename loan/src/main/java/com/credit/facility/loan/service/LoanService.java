package com.credit.facility.loan.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.credit.facility.loan.dto.ApplyLoanDto;
import com.credit.facility.loan.dto.CalculateLateFeeResponseDto;
import com.credit.facility.loan.dto.CalculateLoanResponseDto;
import com.credit.facility.loan.dto.LoanDto;
import com.credit.facility.loan.dto.PayInstallmentResponseDto;
import com.credit.facility.loan.dto.PayLoanResponseDto;
import com.credit.facility.loan.entity.LoanEntity;
import com.credit.facility.loan.entity.LoanPaymentEntity;
import com.credit.facility.loan.enums.LoanStatusType;
import com.credit.facility.loan.mapper.LoanMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LoanService {

	private final LoanValidationService loanValidationService;
	private final LoanEntityService loanEntityService;
	private final LoanPaymentEntityService loanPaymentEntityService;

	private final BigDecimal INTEREST_RATE = BigDecimal.valueOf(1.59 / 100);
	private final BigDecimal TAX_RATE = BigDecimal.valueOf(20 / 100);
	private final BigDecimal ALLOCATION_FEE = BigDecimal.valueOf(45);
	private final int INSTALLMENT_COUNT_LIMIT = 360;

	public CalculateLoanResponseDto calculateLoan(Integer installment, BigDecimal principalLoanAmount) {

		loanValidationService.controlIsParameterNotNull(installment, principalLoanAmount);

		BigDecimal installmentCount = BigDecimal.valueOf(installment);

		BigDecimal totalInterestRate = INTEREST_RATE.add(TAX_RATE);

		BigDecimal maturity = (installmentCount.multiply(BigDecimal.valueOf(30))).divide(BigDecimal.valueOf(36500),
				RoundingMode.CEILING);

		BigDecimal totalInterest = (principalLoanAmount.multiply(totalInterestRate)).multiply(maturity)
				.multiply(installmentCount);
		BigDecimal totalPayment = principalLoanAmount.add(totalInterest).add(ALLOCATION_FEE);

		BigDecimal monthlyInstallmentAmount = totalPayment.divide(installmentCount, RoundingMode.CEILING);

		BigDecimal annualCostRate = totalInterestRate.multiply(BigDecimal.valueOf(12));

		loanValidationService.controlIsInterestRateNotNegative(INTEREST_RATE);
		loanValidationService.controlIsTaxRateNotNegative(TAX_RATE);
		loanValidationService.controlIsInstallmentAmountPositive(monthlyInstallmentAmount);
		loanValidationService.controlIsTotalPaymentPositive(totalPayment);

		CalculateLoanResponseDto calculateLoanResponseDto = new CalculateLoanResponseDto();

		calculateLoanResponseDto.setInterestRate(INTEREST_RATE);
		calculateLoanResponseDto.setTotalInterest(totalInterest);
		calculateLoanResponseDto.setMonthlyInstallmentAmount(monthlyInstallmentAmount);
		calculateLoanResponseDto.setTotalPayment(totalPayment);
		calculateLoanResponseDto.setAnnualCostRate(annualCostRate);
		calculateLoanResponseDto.setAllocationFee(ALLOCATION_FEE);

		return calculateLoanResponseDto;
	}

	public CalculateLateFeeResponseDto calculateLateFee(Long id) {

		LoanEntity loanEntity = loanEntityService.getByIdWithControl(id);

		return calculateLateFeeAndUpdateLoan(loanEntity);
	}

	private CalculateLateFeeResponseDto calculateLateFeeAndUpdateLoan(LoanEntity loanEntity) {

		LocalDate dueDate = loanEntity.getDueDate();

		Long lateDayCount = loanValidationService.controlIsLoanDueDatePast(dueDate);

		BigDecimal totalLoan = loanEntity.getPrincipalLoanAmount();

		BigDecimal lateFeeRate = INTEREST_RATE.add((INTEREST_RATE.multiply(BigDecimal.valueOf(30 / 100))));
		BigDecimal totalLateFee = ((totalLoan.multiply(BigDecimal.valueOf(lateDayCount))).multiply(lateFeeRate))
				.divide(BigDecimal.valueOf(30), RoundingMode.UP);

		BigDecimal lateInterestTax = totalLateFee.multiply(TAX_RATE);

		totalLateFee = totalLateFee.add(lateInterestTax);

		BigDecimal remainingPrincipal = loanEntity.getRemainingPrincipal();
		remainingPrincipal = remainingPrincipal.add(totalLateFee);

		loanValidationService.controlIsInterestRateNotNegative(INTEREST_RATE);
		loanValidationService.controlIsLateFeeRateNotNegative(lateFeeRate);
		loanValidationService.controlIsTotalLateFeePositive(totalLateFee);
		loanValidationService.controlIsLateInterestTaxNotNegative(lateInterestTax);
		loanValidationService.controlIsPrincipalLoanAmountPositive(remainingPrincipal);

		loanEntity.setLoanStatusType(LoanStatusType.LATE);
		loanEntity.setRemainingPrincipal(remainingPrincipal);

		loanEntityService.save(loanEntity);

		CalculateLateFeeResponseDto calculateLateFeeResponseDto = new CalculateLateFeeResponseDto();

		calculateLateFeeResponseDto.setLateFeeRate(lateFeeRate);
		calculateLateFeeResponseDto.setTotalLateFee(totalLateFee);
		calculateLateFeeResponseDto.setLateInterestTax(lateInterestTax);
		calculateLateFeeResponseDto.setLateDayCount(lateDayCount);

		return calculateLateFeeResponseDto;
	}

	public LoanDto findLoanById(Long id) {

		LoanEntity loanEntity = loanEntityService.getByIdWithControl(id);

		updateLoanIfDueDatePast(loanEntity);
		loanEntity = loanEntityService.getByIdWithControl(id);

		LoanDto loanDto = LoanMapper.INSTANCE.convertToLoanDto(loanEntity);

		return loanDto;
	}

	public LoanDto applyLoan(ApplyLoanDto applyLoanDto) {

		loanValidationService.controlIsParameterNotNull(applyLoanDto);

		Long customerId = loanEntityService.getCustomerId();
		BigDecimal principalLoanAmount = applyLoanDto.getPrincipalLoanAmount();
		Integer installment = applyLoanDto.getInstallmentCount();
		BigDecimal installmentCount = BigDecimal.valueOf(installment);
		BigDecimal monthlySalary = applyLoanDto.getMonthlySalary();

		LoanEntity loanEntity = LoanMapper.INSTANCE.convertToLoan(applyLoanDto);

		BigDecimal totalInterestRate = INTEREST_RATE.add(TAX_RATE);

		BigDecimal maturity = (installmentCount.multiply(BigDecimal.valueOf(30))).divide(BigDecimal.valueOf(36500),
				RoundingMode.CEILING);
		BigDecimal totalInterest = (principalLoanAmount.multiply(totalInterestRate)).multiply(maturity)
				.multiply(installmentCount);

		BigDecimal totalPayment = principalLoanAmount.add(totalInterest).add(ALLOCATION_FEE);

		BigDecimal monthlyInstallmentAmount = totalPayment.divide(installmentCount, RoundingMode.CEILING);

		BigDecimal maxInstallmentAmount = monthlySalary.multiply(BigDecimal.valueOf(0.5));
		BigDecimal maxLoanAmount = (maxInstallmentAmount.multiply(installmentCount)).multiply(BigDecimal.valueOf(0.80));

		LocalDate dueDate = LocalDate.now().plusMonths(installment);

		loanValidationService.controlIsInterestRateNotNegative(INTEREST_RATE);
		loanValidationService.controlIsMonthlyInstallmentAmountPositive(monthlyInstallmentAmount);
		loanValidationService.controlIsInterestAmountNotNegative(totalInterest);
		loanValidationService.controlIsPrincipalLoanAmountPositive(principalLoanAmount);
		loanValidationService.controlIsLoanAmountNotGreaterThanMaxLoanAmount(principalLoanAmount, maxLoanAmount);
		loanValidationService.controlIsInstallmentCountNotGreaterThanInstallmentCountLimit(installment,
				INSTALLMENT_COUNT_LIMIT);

		loanEntity.setCustomerId(customerId);
		loanEntity.setMonthlyInstallmentAmount(monthlyInstallmentAmount);
		loanEntity.setInterestToBePaid(totalInterest);
		loanEntity.setPrincipalToBePaid(principalLoanAmount);
		loanEntity.setRemainingPrincipal(principalLoanAmount);
		loanEntity.setDueDate(dueDate);
		loanEntity.setLoanStatusType(LoanStatusType.CONTINUING);

		loanEntity = loanEntityService.save(loanEntity);

		LoanDto loaLoanDto = LoanMapper.INSTANCE.convertToLoanDto(loanEntity);

		return loaLoanDto;
	}

	public PayInstallmentResponseDto payInstallment(Long id) {

		LoanEntity loanEntity = loanEntityService.getByIdWithControl(id);

		updateLoanIfDueDatePast(loanEntity);

		BigDecimal installmentAmount = loanEntity.getMonthlyInstallmentAmount();
		BigDecimal remainingPrincipal = loanEntity.getRemainingPrincipal();

		remainingPrincipal = remainingPrincipal.subtract(installmentAmount);

		loanValidationService.controlIsRemainingPrincipalNotNegative(remainingPrincipal);
		loanValidationService.controlIsInstallmentAmountPositive(installmentAmount);

		loanEntity.setRemainingPrincipal(remainingPrincipal);

		LoanPaymentEntity loanPayment = new LoanPaymentEntity();

		loanPayment.setLoanId(id);
		loanPayment.setPaymentAmount(installmentAmount);
		loanPayment.setPaymentDate(LocalDate.now());

		loanEntity = loanEntityService.save(loanEntity);
		loanPayment = loanPaymentEntityService.save(loanPayment);

		PayInstallmentResponseDto payInstallmentResponseDto = convertToLoaPayInstallmentResponseDto(loanEntity,
				loanPayment);

		return payInstallmentResponseDto;
	}

	private void updateLoanIfDueDatePast(LoanEntity loanEntity) {

		LocalDate dueDate = loanEntity.getDueDate();

		long lateDayCount = ChronoUnit.DAYS.between(dueDate, LocalDate.now());

		if (lateDayCount > 0) {
			calculateLateFeeAndUpdateLoan(loanEntity);
		}
	}

	private PayInstallmentResponseDto convertToLoaPayInstallmentResponseDto(LoanEntity loanEntity,
			LoanPaymentEntity loanPayment) {

		Long loanId = loanPayment.getLoanId();
		BigDecimal paymentAmount = loanPayment.getPaymentAmount();
		LocalDate PaymentDate = loanPayment.getPaymentDate();

		BigDecimal remainingPrincipal = loanEntity.getRemainingPrincipal();
		LocalDate dueDate = loanEntity.getDueDate();

		PayInstallmentResponseDto payInstallmentResponseDto = new PayInstallmentResponseDto();

		payInstallmentResponseDto.setLoanId(loanId);
		payInstallmentResponseDto.setPaymentAmount(paymentAmount);
		payInstallmentResponseDto.setPaymentDate(PaymentDate);
		payInstallmentResponseDto.setRemainingPrincipal(remainingPrincipal);
		payInstallmentResponseDto.setDueDate(dueDate);

		return payInstallmentResponseDto;
	}

	public PayLoanResponseDto payLoan(Long id) {

		LoanEntity loanEntity = loanEntityService.getByIdWithControl(id);

		updateLoanIfDueDatePast(loanEntity);

		BigDecimal paidAmount = loanEntity.getRemainingPrincipal();
		BigDecimal remainingPrincipal = BigDecimal.ZERO;

		loanValidationService.controlIsLoanNotAlreadyPaidOff(loanEntity);
		loanValidationService.controlIsRemainingPrincipalNotNegative(remainingPrincipal);

		loanEntity.setRemainingPrincipal(remainingPrincipal);
		loanEntity.setLoanStatusType(LoanStatusType.PAID);

		loanEntity = loanEntityService.save(loanEntity);

		PayLoanResponseDto payLoanOffResponseDto = LoanMapper.INSTANCE
				.convertToPayLoanOffResponseDto(loanEntity);

		payLoanOffResponseDto.setRemainingAmount(remainingPrincipal);
		payLoanOffResponseDto.setPaidAmount(paidAmount);

		return payLoanOffResponseDto;
	}
}
