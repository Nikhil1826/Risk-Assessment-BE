package com.grc.risk.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.grc.risk.dto.MalformedRequestErrorDetails;

@RestControllerAdvice
public class GrcExceptionHandler {

	@ExceptionHandler(UserUnauthorizedException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public GenericError handleUnauthorizedUserException(UserUnauthorizedException ex) {
		return ex.getGenericError();
	}

	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public GenericError handleBadRequestException(BadRequestException ex) {
		return ex.getGenericError();
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public GenericError handleResourceNotFoundException(ResourceNotFoundException ex) {
		return ex.getGenericError();
	}

	@ExceptionHandler(InternalServerException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public GenericError handleInternalServerException(InternalServerException ex) {
		return ex.getGenericError();
	}

	@ExceptionHandler(MalformedRequestBodyException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public List<MalformedRequestErrorDetails> handleMalformedRequestBodyException(MalformedRequestBodyException ex) {
		return ex.getFieldsInError();
	}

}
