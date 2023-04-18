package com.credit.facility.loan.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.credit.facility.loan.dto.ApplyLoanDto;
import com.credit.facility.loan.dto.LoanDto;
import com.credit.facility.loan.dto.PayLoanResponseDto;
import com.credit.facility.loan.entity.LoanEntity;

@Mapper
public interface LoanMapper {

    LoanMapper INSTANCE = Mappers.getMapper(LoanMapper.class);

    LoanEntity convertToLoan(ApplyLoanDto loanApplyLoanDto);

    LoanDto convertToLoanDto(LoanEntity loanEntity);

    PayLoanResponseDto convertToPayLoanOffResponseDto(LoanEntity loanEntity);
}
