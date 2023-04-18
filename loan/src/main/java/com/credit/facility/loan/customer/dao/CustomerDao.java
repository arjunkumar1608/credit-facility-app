package com.credit.facility.loan.customer.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.credit.facility.loan.customer.entity.CustomerEntity;

public interface CustomerDao extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findById(Long id);
}
