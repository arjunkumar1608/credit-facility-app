package com.credit.facilty.loan.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.credit.facilty.loan.entity.LoanEntity;

public interface LoanDao extends JpaRepository<LoanEntity, Long> {
}
