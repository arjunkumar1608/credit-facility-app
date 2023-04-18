package com.credit.facility.loan.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.credit.facility.loan.util.enums.BaseErrorMessage;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalFieldException extends BusinessException{

    private static final long serialVersionUID = 1L;

	public IllegalFieldException(BaseErrorMessage message) {
        super(message);
    }
}
