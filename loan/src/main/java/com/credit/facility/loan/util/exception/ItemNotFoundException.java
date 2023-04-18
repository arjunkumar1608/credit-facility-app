package com.credit.facility.loan.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.credit.facility.loan.util.enums.BaseErrorMessage;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemNotFoundException extends BusinessException{

    private static final long serialVersionUID = 1L;

	public ItemNotFoundException(BaseErrorMessage message) {
        super(message);
    }
}
