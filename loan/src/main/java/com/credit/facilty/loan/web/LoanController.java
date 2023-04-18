package com.credit.facilty.loan.web;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.credit.facilty.loan.dto.ApplyLoanDto;
import com.credit.facilty.loan.dto.CalculateLateFeeResponseDto;
import com.credit.facilty.loan.dto.CalculateLoanResponseDto;
import com.credit.facilty.loan.dto.LoanDto;
import com.credit.facilty.loan.dto.PayInstallmentResponseDto;
import com.credit.facilty.loan.dto.PayLoanResponseDto;
import com.credit.facilty.loan.service.LoanService;
import com.credit.facilty.loan.util.RestResponse;

@RestController
@RequestMapping("credit/v1/loans")
public class LoanController {

	private final LoanService loanService;

	public LoanController(LoanService loanService) {
		super();
		this.loanService = loanService;
	}
	
	//For testing
	@GetMapping
	public String getData(@RequestParam String data) {
		System.out.println("Data is " + data);
		return data;
	}

	@GetMapping("/calculate/loan")
	public ResponseEntity<RestResponse<CalculateLoanResponseDto>> calculateLoan(
			@RequestParam BigDecimal principalLoanAmount, @RequestParam Integer installmentCount) {

		CalculateLoanResponseDto calculateLoanResponseDto = loanService.calculateLoan(installmentCount,
				principalLoanAmount);

		return ResponseEntity.ok(RestResponse.of(calculateLoanResponseDto));
	}

	@GetMapping("/calculate/late-fee/{id}")
	public ResponseEntity<RestResponse<CalculateLateFeeResponseDto>> calculateLateFee(@PathVariable Long id) {

		CalculateLateFeeResponseDto calculateLateFeeResponseDto = loanService.calculateLateFee(id);

		return ResponseEntity.ok(RestResponse.of(calculateLateFeeResponseDto));
	}

	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<LoanDto>> findLoanById(@PathVariable Long id) {

		LoanDto loanDto = loanService.findLoanById(id);

		return ResponseEntity.ok(RestResponse.of(loanDto));
	}

	@PostMapping("/apply/loan")
	public ResponseEntity<RestResponse<LoanDto>> applyLoan(@RequestBody ApplyLoanDto applyLoanDto) {

		LoanDto loanDto = loanService.applyLoan(applyLoanDto);

		return ResponseEntity.ok(RestResponse.of(loanDto));
	}

	@PostMapping("/pay/installment/{id}")
	public ResponseEntity<RestResponse<PayInstallmentResponseDto>> payInstallment(@PathVariable Long id) {

		PayInstallmentResponseDto payInstallmentResponseDto = loanService.payInstallment(id);

		return ResponseEntity.ok(RestResponse.of(payInstallmentResponseDto));
	}

	@DeleteMapping("/pay/loan/{id}")
	public ResponseEntity<RestResponse<PayLoanResponseDto>> closeLoan(@PathVariable Long id) {

		PayLoanResponseDto payLoanResponseDto = loanService.payLoan(id);

		return ResponseEntity.ok(RestResponse.of(payLoanResponseDto));
	}
}
