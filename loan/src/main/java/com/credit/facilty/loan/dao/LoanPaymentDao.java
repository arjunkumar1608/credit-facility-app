package com.credit.facilty.loan.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.credit.facilty.loan.entity.LoanPaymentEntity;

public interface LoanPaymentDao extends JpaRepository<LoanPaymentEntity, Long> {
}
