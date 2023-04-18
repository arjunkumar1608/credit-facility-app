package com.credit.facility.loan.util.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.credit.facility.loan.util.RestResponse;

import java.util.Date;

@RestController
@ControllerAdvice
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest webRequest) {

		Date errorDate = new Date();
		String message = ex.getMessage();
		String description = webRequest.getDescription(false);

		ExceptionResponse genExceptionResponse = new ExceptionResponse(errorDate, message, description);

		RestResponse<ExceptionResponse> restResponse = RestResponse.error(genExceptionResponse);
		restResponse.setMessages(message);

		return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler
	public final ResponseEntity<Object> handleAllItemNotFoundException(ItemNotFoundException ex,
			WebRequest webRequest) {

		Date errorDate = new Date();
		String message = ex.getBaseErrorMessage().getMessage();
		String description = ex.getBaseErrorMessage().getDetailMessage();

		ExceptionResponse genExceptionResponse = new ExceptionResponse(errorDate, message, description);

		RestResponse<ExceptionResponse> restResponse = RestResponse.error(genExceptionResponse);
		restResponse.setMessages(message);

		return new ResponseEntity<>(restResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public final ResponseEntity<Object> handleAllGenBusinessException(BusinessException ex, WebRequest webRequest) {

		Date errorDate = new Date();
		String message = ex.getBaseErrorMessage().getMessage();
		String description = ex.getBaseErrorMessage().getDetailMessage();

		ExceptionResponse genExceptionResponse = new ExceptionResponse(errorDate, message, description);

		RestResponse<ExceptionResponse> restResponse = RestResponse.error(genExceptionResponse);
		restResponse.setMessages(message);

		return new ResponseEntity<>(restResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public final ResponseEntity<Object> handleAllIllegalFieldException(IllegalFieldException ex,
			WebRequest webRequest) {

		Date errorDate = new Date();
		String message = ex.getBaseErrorMessage().getMessage();
		String description = ex.getBaseErrorMessage().getDetailMessage();

		ExceptionResponse genExceptionResponse = new ExceptionResponse(errorDate, message, description);

		RestResponse<ExceptionResponse> restResponse = RestResponse.error(genExceptionResponse);
		restResponse.setMessages(message);

		return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Date errorDate = new Date();
		String message = "Validation failed!";
		String description = ex.getBindingResult().toString();

		ExceptionResponse genExceptionResponse = new ExceptionResponse(errorDate, message, description);
		RestResponse<ExceptionResponse> restResponse = RestResponse.error(genExceptionResponse);
		restResponse.setMessages(message);

		return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
	}
}