package com.grc.risk.dto;

public class MalformedRequestErrorDetails {
	
	private String fieldName;
	private String error;
	private String userAction;

	public MalformedRequestErrorDetails(String fieldName, String error, String userAction) {
		this.fieldName = fieldName;
		this.error = error;
		this.userAction = userAction;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getUserAction() {
		return userAction;
	}

	public String getError() {
		return error;
	}
}
