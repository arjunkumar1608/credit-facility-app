package com.credit.facility.loan.util.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.credit.facility.loan.util.enums.BaseErrorMessage;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@RequiredArgsConstructor
@Getter
@Setter
public class BusinessException extends RuntimeException{

    private static final long serialVersionUID = 1L;
	private final BaseErrorMessage baseErrorMessage;
}
