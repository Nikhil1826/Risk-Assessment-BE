package com.grc.risk.exception;

public class BadRequestException extends Exception {
	private static final long serialVersionUID = 1564946667270815133L;

	private GenericError genericError;

	public BadRequestException() {
		genericError = new GenericError();
	}

	public BadRequestException(String message) {
		super(message);
	}

	public BadRequestException(String message, Throwable error) {
		super(message, error);
	}

	public GenericError getGenericError() {
		return genericError;
	}

	public void setGenericError(GenericError genericError) {
		this.genericError = genericError;
	}

}
