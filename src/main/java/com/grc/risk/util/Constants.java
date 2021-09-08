package com.grc.risk.util;

public class Constants {

	private Constants() {

	}

	// 604800000L

	public static final String RISK_ASSESSEMENT_SUCCESS = "Assessement score saved successfully.";
	public static final Long SEVEN_DAYS_MILLISECONDS = 300000L;

	// ERROR CODES
	public static final String GRC_USER_ERR_CODE = "GRC_USER_ERR";
	public static final String GRC_RISK_ERR_CODE = "GRC_RISK_ERR";
	public static final String GRC_USER_RISK_ERR_CODE = "GRC_USER_RISK_ERR";
	public static final String GRC_SOMETHING_WENT_WRONG_ERR_CODE = "GRC_INTERNAL_SERVER_ERR";

	// ERROR MESSAGES
	public static final String LOGIN_FAILED_ERR_MSG = "Failed to authenticate user.";
	public static final String USER_NOT_FOUND_ERR_MSG = "User not found.";
	public static final String NO_RISKS_ASSIGNED_ERR_MSG = "No risks are assigned to the risk manager.";
	public static final String USER_RISK_MAPPING_NOT_FOUND_ERR_MSG = "User Risk mapping not found.";
	public static final String USER_UNAUTHORIZED_ERR_MSG = "You are not authorized to perform this action.";
	public static final String NO_USER_FOUND_ERR_MSG = "No users found.";
	public static final String NO_RISK_FOUND_ERR_MSG = "No risks found.";
	public static final String RISK_NOT_FOUND = "Risk not found.";
	public static final String SOMETHING_WENT_WRONG_ERR_MSG = "Something went wrong.";

	// USER ACTIONS
	public static final String LOGIN_FAILED_USER_ACTION = "Please validate the credentials.";
	public static final String USER_NOT_FOUND_USER_ACTION = "Please provide valid user Id.";
	public static final String NO_RISKS_ASSIGNED_USER_ACTION = "Please ask admin for risk assignment.";
	public static final String USER_RISK_MAPPING_NOT_FOUND_USER_ACTION = "Please provide valid mapping Id.";
	public static final String CONTACT_ADMIN_USER_ACTION = "Please contact admin for further details.";
	public static final String RISK_NOT_FOUND_USER_ACTION = "Please provide valid risk Id.";
	public static final String SOMETHING_WENT_WRONG_USER_ACTION = "Please try again later.";
	
	public static final String RISK_ADD_SUCCESS = "Risk added successfully.";
	public static final String RISK_UPDATE_SUCCESS = "Risk updated successfully.";
	public static final String RISK_DELETE_SUCCESS = "Risk deleted successfully.";
}
