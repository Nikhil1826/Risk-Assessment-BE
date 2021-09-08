package com.grc.risk.enums;

public enum ModeType {
	ADD("ADD"), UPDATE("UPDATE");

	private String mode;

	ModeType(String mode) {
		this.mode = mode;
	}

	public String getModeType() {
		return mode;
	}
}
