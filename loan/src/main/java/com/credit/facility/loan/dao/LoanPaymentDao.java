package com.credit.facility.loan.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.credit.facility.loan.entity.LoanPaymentEntity;

public interface LoanPaymentDao extends JpaRepository<LoanPaymentEntity, Long> {
}
