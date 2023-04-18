package com.credit.facilty.loan.customer.mapper;

import com.credit.facilty.loan.customer.dto.CustomerDto;
import com.credit.facilty.loan.customer.dto.CustomerSaveDto;
import com.credit.facilty.loan.customer.dto.CustomerUpdateDto;
import com.credit.facilty.loan.customer.entity.CustomerEntity;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    List<CustomerDto> convertToCustomerDtoList(List<CustomerEntity> customerList);

    CustomerDto convertToCustomerDto(CustomerEntity customer);

    CustomerEntity convertToCustomer(CustomerSaveDto customerSaveDto);

    CustomerEntity convertToCustomer(CustomerUpdateDto customerUpdateDto);
}
