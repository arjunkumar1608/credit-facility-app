
package com.credit.facilty.loan.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.credit.facilty.loan.customer.dto.CustomerDto;
import com.credit.facilty.loan.customer.dto.CustomerSaveDto;
import com.credit.facilty.loan.security.config.AuthenticationService;
import com.credit.facilty.loan.security.dto.LoginRequestDto;
import com.credit.facilty.loan.util.RestResponse;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/credit/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthenticationService authenticationService;

	@PostMapping("/login")
	public ResponseEntity<RestResponse<String>> login(@RequestBody LoginRequestDto loginRequestDto) {

		String token = authenticationService.login(loginRequestDto);

		return ResponseEntity.ok(RestResponse.of(token));
	}

	@Operation(tags = "Authentication Controller")

	@PostMapping("/register")
	public ResponseEntity<RestResponse<CustomerDto>> register(@RequestBody CustomerSaveDto customerSaveDto) {

		CustomerDto customerDto = authenticationService.register(customerSaveDto);

		return ResponseEntity.ok(RestResponse.of(customerDto));
	}
}
