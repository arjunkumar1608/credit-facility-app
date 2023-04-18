package com.credit.facility.loan.customer.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.credit.facility.loan.customer.entity.CustomerEntity;
import com.credit.facility.loan.util.enums.CustomerErrorMessage;
import com.credit.facility.loan.util.exception.IllegalFieldException;
import com.credit.facility.loan.util.exception.ItemNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerValidationService {

	private final CustomerEntityService customerEntityService;

	public void isCustomerExist(Long id) {

		boolean isExist = customerEntityService.existsById(id);

		if (!isExist){

			throw new ItemNotFoundException(CustomerErrorMessage.CUSTOMER_NOT_FOUND);
		}

	}

	public void controlAreFieldsNonNull(CustomerEntity customer) {
		boolean hasNullField =
				customer.getName().isEmpty() ||
				customer.getSurname().isEmpty()  ||
				customer.getIdentity() == null ||
				customer.getPassword().isEmpty();

		if(hasNullField){
			throw new IllegalFieldException(CustomerErrorMessage.FIELD_CANNOT_BE_NULL);
		}
	}

	public void controlIsIdentityNoUnique(CustomerEntity customer) {

		Optional<CustomerEntity> customerOptional = customerEntityService.findByIdentity(customer);

		CustomerEntity customerReturned;
		if(customerOptional.isPresent()){

			customerReturned = customerOptional.get();

			boolean didMatchedItself = isMatched(customerReturned, customer);

			if(!didMatchedItself){
				throw new IllegalFieldException(CustomerErrorMessage.IDENTITY_NO_MUST_BE_UNIQUE);
			}
		}
	}

	private Boolean isMatched(CustomerEntity userReturned, CustomerEntity customer) {

		return userReturned.getId().equals(customer.getId());
	}
}
