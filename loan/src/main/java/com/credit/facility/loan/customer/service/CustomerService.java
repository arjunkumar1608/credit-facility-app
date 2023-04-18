package com.credit.facility.loan.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.credit.facility.loan.customer.dto.CustomerDto;
import com.credit.facility.loan.customer.dto.CustomerSaveDto;
import com.credit.facility.loan.customer.dto.CustomerUpdateDto;
import com.credit.facility.loan.customer.entity.CustomerEntity;
import com.credit.facility.loan.customer.mapper.CustomerMapper;


@Service
@Transactional
public class CustomerService {

	public final CustomerEntityService customerEntityService;
    public final CustomerValidationService customerValidationService;
    
    public CustomerService(CustomerEntityService customerEntityService,
			CustomerValidationService customerValidationService) {
		super();
		this.customerEntityService = customerEntityService;
		this.customerValidationService = customerValidationService;
	}
	

    public List<CustomerDto> findAllCustomers() {

        List<CustomerEntity> customerList = customerEntityService.findAllCustomers();

        List<CustomerDto> customerDtoList = CustomerMapper.INSTANCE.convertToCustomerDtoList(customerList);

        return customerDtoList;
    }

    public CustomerDto findCustomerById(Long id) {

        CustomerEntity customerEntity = customerEntityService.getByIdWithControl(id);

        CustomerDto customerDto = CustomerMapper.INSTANCE.convertToCustomerDto(customerEntity);

        return customerDto;
    }

    public CustomerDto saveCustomer(CustomerSaveDto customerSaveDto) {

        CustomerEntity customerEntity = CustomerMapper.INSTANCE.convertToCustomer(customerSaveDto);

        customerEntity.setPassword(customerEntity.getPassword());

        customerValidationService.controlAreFieldsNonNull(customerEntity);
        customerValidationService.controlIsIdentityNoUnique(customerEntity);

        customerEntity = customerEntityService.saveCustomer(customerEntity);

        CustomerDto customerDto = CustomerMapper.INSTANCE.convertToCustomerDto(customerEntity);

        return customerDto;
    }

    public CustomerDto updateCustomer(CustomerUpdateDto customerUpdateDto) {

        Long id = customerUpdateDto.getId();
        customerValidationService.isCustomerExist(id);

        CustomerEntity customerEntity = CustomerMapper.INSTANCE.convertToCustomer(customerUpdateDto);

        customerValidationService.controlAreFieldsNonNull(customerEntity);
        customerValidationService.controlIsIdentityNoUnique(customerEntity);

        String password = customerEntityService.findCustomerById(id).getPassword();
        boolean isPasswordSame = customerEntity.getPassword().equals(password);

        if(!isPasswordSame){
            customerEntity.setPassword(customerEntity.getPassword());
        }

        customerEntity = customerEntityService.saveCustomer(customerEntity);

        CustomerDto customerDto = CustomerMapper.INSTANCE.convertToCustomerDto(customerEntity);

        return customerDto;
    }

    public void deleteCustomer(Long id) {

        CustomerEntity customerEntity = customerEntityService.getByIdWithControl(id);

        customerEntityService.delete(customerEntity);
    }
}
