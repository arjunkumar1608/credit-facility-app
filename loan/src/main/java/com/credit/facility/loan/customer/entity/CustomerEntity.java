package com.credit.facility.loan.customer.entity;

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
@Table(name="CUSTOMER")
public class CustomerEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

	@Id
    @SequenceGenerator(name = "Customer" , sequenceName = "CUSTOMER_ID_SEQ")
    @GeneratedValue(generator = "Customer")
    private Long id;

    @Column(name="NAME",length=100, nullable = false)
    private String name;

    @Column(name="SURNAME",length=100, nullable = false)
    private String surname;

    @Column(name="IDENTITY",nullable = false,unique = true)
    private Long identity;

    @Column(name = "PASSWORD",length=100, nullable = false)
    private String password;

}
