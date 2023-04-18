package com.credit.facilty.loan.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.credit.facilty.loan.dao.LoanPaymentDao;
import com.credit.facilty.loan.entity.LoanPaymentEntity;
import com.credit.facilty.loan.util.service.BaseEntityService;

@Service
@Transactional
public class LoanPaymentEntityService extends BaseEntityService<LoanPaymentEntity, LoanPaymentDao> {

    public LoanPaymentEntityService(LoanPaymentDao dao){
        super(dao);
    }
}
