package com.grc.risk.exception;

public class UserUnauthorizedException extends Exception {
	private static final long serialVersionUID = -6031663038207258802L;

	private GenericError genericError;

	public UserUnauthorizedException() {
		genericError = new GenericError();
	}

	public UserUnauthorizedException(String message) {
		super(message);
	}

	public UserUnauthorizedException(String message, Throwable error) {
		super(message, error);
	}

	public GenericError getGenericError() {
		return genericError;
	}

	public void setGenericError(GenericError genericError) {
		this.genericError = genericError;
	}

}
