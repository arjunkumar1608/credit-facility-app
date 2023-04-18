package com.credit.facility.loan.service;

import com.credit.facility.loan.dao.LoanDao;
import com.credit.facility.loan.entity.LoanEntity;
import com.credit.facility.loan.util.service.BaseEntityService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoanEntityService extends BaseEntityService<LoanEntity, LoanDao> {

    public LoanEntityService(LoanDao dao){
        super(dao);
    }
}
