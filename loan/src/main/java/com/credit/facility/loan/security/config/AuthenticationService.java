package com.credit.facility.loan.security.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.credit.facility.loan.customer.dto.CustomerDto;
import com.credit.facility.loan.customer.dto.CustomerSaveDto;
import com.credit.facility.loan.customer.entity.CustomerEntity;
import com.credit.facility.loan.customer.service.CustomerEntityService;
import com.credit.facility.loan.customer.service.CustomerService;
import com.credit.facility.loan.security.JwtTokenGenerator;
import com.credit.facility.loan.security.JwtUserDetails;
import com.credit.facility.loan.security.dto.LoginRequestDto;
import com.credit.facility.loan.security.enums.JwtEnums;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthenticationService {

	private final CustomerService customerService;
	private final CustomerEntityService customerEntityService;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenGenerator jwtTokenGenerator;

	public CustomerDto register(CustomerSaveDto customerSaveDto) {

		return customerService.saveCustomer(customerSaveDto);
	}

	public String login(LoginRequestDto loginRequestDto) {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginRequestDto.getIdentity().toString(), loginRequestDto.getPassword());

		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtTokenGenerator.generateJwtToken(authentication);

		String bearer = JwtEnums.BEARER.getConstant();

		return bearer + token;
	}

	public CustomerEntity getCurrentCustomer() {

		JwtUserDetails jwtUserDetails = getCurrentJwtUserDetails();

		CustomerEntity customerEntity = null;
		if (jwtUserDetails != null) {
			customerEntity = customerEntityService.getByIdWithControl(jwtUserDetails.getId());
		}

		return customerEntity;
	}

	public Long getCustomerId() {

		JwtUserDetails jwtUserDetails = getCurrentJwtUserDetails();

		Long jwtUserDetailsId = null;
		if (jwtUserDetails != null) {
			jwtUserDetailsId = jwtUserDetails.getId();
		}

		return jwtUserDetailsId;
	}

	private JwtUserDetails getCurrentJwtUserDetails() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		JwtUserDetails jwtUserDetails = null;
		if (authentication != null && authentication.getPrincipal() instanceof JwtUserDetails) {
			jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
		}
		return jwtUserDetails;
	}
}
