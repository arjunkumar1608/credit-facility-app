package com.credit.facility.loan.customer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.credit.facility.loan.customer.dao.CustomerDao;
import com.credit.facility.loan.customer.entity.CustomerEntity;
import com.credit.facility.loan.util.enums.CustomerErrorMessage;
import com.credit.facility.loan.util.exception.ItemNotFoundException;
import com.credit.facility.loan.util.service.BaseEntityService;

@Service
@Transactional
public class CustomerEntityService extends BaseEntityService<CustomerEntity, CustomerDao> {

    public CustomerEntityService(CustomerDao customerDao) {
        super(customerDao);
    }

    public List<CustomerEntity> findAllCustomers() {

        List<CustomerEntity> customerList = getDao().findAll();

        return customerList;
    }

    public CustomerEntity saveCustomer(CustomerEntity customerEntity) {

    	customerEntity = getDao().save(customerEntity);

        return customerEntity;
    }

    public CustomerEntity findByIdentity(Long id) {

        CustomerEntity customerEntity = getDao().findById(id)
                .orElseThrow(()-> new ItemNotFoundException(CustomerErrorMessage.CUSTOMER_NOT_FOUND));

       return customerEntity;
    }

    public Optional<CustomerEntity> findByIdentity(CustomerEntity customerEntity) {

        Long identity = customerEntity.getIdentity();

        Optional<CustomerEntity> customerOptional = getDao().findById(identity);

        return customerOptional;
    }

    public CustomerEntity findCustomerById(Long id){

        CustomerEntity customerEntity = super.findById(id)
                .orElseThrow(()-> new ItemNotFoundException(CustomerErrorMessage.CUSTOMER_NOT_FOUND));

        return customerEntity;
    }
}
