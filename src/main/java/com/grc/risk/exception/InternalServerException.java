package com.grc.risk.exception;

public class InternalServerException extends Exception {
	private static final long serialVersionUID = -689003235632829245L;

	private GenericError genericError;

	public InternalServerException() {
		genericError = new GenericError();
	}

	public InternalServerException(String message) {
		super(message);
	}

	public InternalServerException(String message, Throwable error) {
		super(message, error);
	}

	public GenericError getGenericError() {
		return genericError;
	}

	public void setGenericError(GenericError genericError) {
		this.genericError = genericError;
	}

}
