package com.credit.facilty.loan.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.credit.facilty.loan.customer.dto.CustomerDto;
import com.credit.facilty.loan.customer.dto.CustomerSaveDto;
import com.credit.facilty.loan.customer.dto.CustomerUpdateDto;
import com.credit.facilty.loan.customer.service.CustomerService;
import com.credit.facilty.loan.util.RestResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("credit/v1/customers")
public class CustomerController {

	private final CustomerService customerService;

	@GetMapping
	public ResponseEntity<RestResponse<List<CustomerDto>>> getAllCustomers() {

		List<CustomerDto> customerDtoList = customerService.findAllCustomers();

		return ResponseEntity.ok(RestResponse.of(customerDtoList));
	}

	@GetMapping("/{id}")
	public ResponseEntity<RestResponse<CustomerDto>> findCustomerById(@PathVariable Long id) {

		CustomerDto customerDto = customerService.findCustomerById(id);
		return ResponseEntity.ok(RestResponse.of(customerDto));
	}


	@PostMapping("/save/customer")
	public ResponseEntity<RestResponse<CustomerDto>> saveCustomer(@RequestBody CustomerSaveDto customerSaveDto) {

		CustomerDto customerDto = customerService.saveCustomer(customerSaveDto);
		return ResponseEntity.ok(RestResponse.of(customerDto));
	}

	@PutMapping("/update/customer")
	public ResponseEntity<RestResponse<CustomerDto>> updateCustomer(@RequestBody CustomerUpdateDto customerUpdateDto) {

		CustomerDto customerDto = customerService.updateCustomer(customerUpdateDto);

		return ResponseEntity.ok(RestResponse.of(customerDto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse<?>> deleteCustomer(@PathVariable Long id) {

		customerService.deleteCustomer(id);

		return ResponseEntity.ok(RestResponse.empty());
	}
}
