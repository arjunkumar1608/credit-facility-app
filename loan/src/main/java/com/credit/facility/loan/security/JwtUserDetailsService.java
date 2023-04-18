package com.credit.facility.loan.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.credit.facility.loan.customer.entity.CustomerEntity;
import com.credit.facility.loan.customer.service.CustomerEntityService;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	private final CustomerEntityService customerEntityService;

	public JwtUserDetailsService(CustomerEntityService customerEntityService) {
		super();
		this.customerEntityService = customerEntityService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Long identity = Long.valueOf(username);

		CustomerEntity customerEntity = customerEntityService.findByIdentity(identity);

		return JwtUserDetails.create(customerEntity);
	}

	public UserDetails loadUserByUserId(Long id) {

		CustomerEntity customerEntity = customerEntityService.getByIdWithControl(id);

		return JwtUserDetails.create(customerEntity);
	}
}
