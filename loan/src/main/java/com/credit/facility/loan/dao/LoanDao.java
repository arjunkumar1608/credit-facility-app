package com.credit.facility.loan.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.credit.facility.loan.entity.LoanEntity;

public interface LoanDao extends JpaRepository<LoanEntity, Long> {
}
