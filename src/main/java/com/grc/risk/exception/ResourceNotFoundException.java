package com.grc.risk.exception;

public class ResourceNotFoundException extends Exception {
	private static final long serialVersionUID = -4389833114972839265L;

	private GenericError genericError;

	public ResourceNotFoundException() {
		genericError = new GenericError();
	}

	public ResourceNotFoundException(String message) {
		super(message);
	}

	public ResourceNotFoundException(String message, Throwable error) {
		super(message, error);
	}

	public GenericError getGenericError() {
		return genericError;
	}

	public void setGenericError(GenericError genericError) {
		this.genericError = genericError;
	}

}
