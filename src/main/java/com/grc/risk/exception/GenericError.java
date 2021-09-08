package com.grc.risk.exception;

public class GenericError {

	private String errorcode;
	private String errormessage;
	private String useraction;

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	public String getErrormessage() {
		return errormessage;
	}

	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}

	public String getUseraction() {
		return useraction;
	}

	public void setUseraction(String useraction) {
		this.useraction = useraction;
	}

}
