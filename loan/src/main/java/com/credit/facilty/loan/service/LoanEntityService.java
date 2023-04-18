package com.credit.facilty.loan.service;

import com.credit.facilty.loan.dao.LoanDao;
import com.credit.facilty.loan.entity.LoanEntity;
import com.credit.facilty.loan.util.service.BaseEntityService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoanEntityService extends BaseEntityService<LoanEntity, LoanDao> {

    public LoanEntityService(LoanDao dao){
        super(dao);
    }
}
