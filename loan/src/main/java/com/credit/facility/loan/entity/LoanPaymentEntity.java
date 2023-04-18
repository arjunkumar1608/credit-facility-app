package com.credit.facility.loan.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.credit.facility.loan.base.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="LOA_LOAN_PAYMENT")
public class LoanPaymentEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

	@Id
    @SequenceGenerator(name="LoaLoanPayment",sequenceName = "LOA_LOAN_PAYMENT_ID_SEQ")
    @GeneratedValue(generator = "LoaLoanPayment")
    private Long id;

    @Column(name="ID_LOA_LOAN",nullable = false)
    private Long loanId;

    @Column(name="PAYMENT_AMOUNT", precision = 19 ,scale =2 ,nullable = false)
    private BigDecimal paymentAmount;

    @Column(name="PAYMENT_DATE",nullable = false)
    private LocalDate PaymentDate;
}
