package com.credit.facility.loan.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.credit.facility.loan.dao.LoanPaymentDao;
import com.credit.facility.loan.entity.LoanPaymentEntity;
import com.credit.facility.loan.util.service.BaseEntityService;

@Service
@Transactional
public class LoanPaymentEntityService extends BaseEntityService<LoanPaymentEntity, LoanPaymentDao> {

    public LoanPaymentEntityService(LoanPaymentDao dao){
        super(dao);
    }
}
