package com.credit.facilty.loan.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.credit.facilty.loan.dto.ApplyLoanDto;
import com.credit.facilty.loan.dto.LoanDto;
import com.credit.facilty.loan.dto.PayLoanResponseDto;
import com.credit.facilty.loan.entity.LoanEntity;

@Mapper
public interface LoanMapper {

    LoanMapper INSTANCE = Mappers.getMapper(LoanMapper.class);

    LoanEntity convertToLoan(ApplyLoanDto loanApplyLoanDto);

    LoanDto convertToLoanDto(LoanEntity loanEntity);

    PayLoanResponseDto convertToPayLoanOffResponseDto(LoanEntity loanEntity);
}
