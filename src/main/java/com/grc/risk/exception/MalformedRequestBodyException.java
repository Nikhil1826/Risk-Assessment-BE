package com.grc.risk.exception;

import java.util.List;
import com.grc.risk.dto.MalformedRequestErrorDetails;

public class MalformedRequestBodyException extends Exception {
	private static final long serialVersionUID = 7199282702603711208L;

	private final List<MalformedRequestErrorDetails> fieldsInError;

	public MalformedRequestBodyException(List<MalformedRequestErrorDetails> fieldsInError) {
		this.fieldsInError = fieldsInError;
	}

	public List<MalformedRequestErrorDetails> getFieldsInError() {
		return fieldsInError;
	}

}
