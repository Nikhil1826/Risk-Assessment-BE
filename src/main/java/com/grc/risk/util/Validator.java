package com.grc.risk.util;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import com.grc.risk.dto.AssessmentRequest;
import com.grc.risk.dto.MalformedRequestErrorDetails;
import com.grc.risk.dto.RiskDTO;
import com.grc.risk.enums.ModeType;

public class Validator {

	private Validator() {
	}

	public static List<MalformedRequestErrorDetails> validateRiskAssessmentRequest(
			AssessmentRequest assessmentRequest) {
		List<MalformedRequestErrorDetails> errorList = new ArrayList<>();

		// Mapping Id is required
		if (assessmentRequest.getMappingId() == null) {
			errorList.add(new MalformedRequestErrorDetails("mappingId", "Mapping Id is mandatory.",
					"Please provide valid mapping Id."));
		}

		// Assessment score is required
		if (assessmentRequest.getAssessmentScore() == null) {
			errorList.add(new MalformedRequestErrorDetails("assessmentScore", "Assessment score is mandatory.",
					"Please provide valid assessment score."));
		} else {
			// Assessment score should be valid
			if (!(assessmentRequest.getAssessmentScore() >= 1 && assessmentRequest.getAssessmentScore() <= 5)) {
				errorList.add(new MalformedRequestErrorDetails("assessmentScore", "Invalid assessment score.",
						"Assessment score should be in the range of 1-5."));
			}
		}
		return errorList;
	}

	/**
	 * This method validates the request body of add request API
	 * 
	 * @param riskDTO
	 * @return
	 */
	public static List<MalformedRequestErrorDetails> validateRiskRequest(RiskDTO riskDTO, String modeType) {
		List<MalformedRequestErrorDetails> errorList = new ArrayList<>();

		// Risk Id is mandatory in update mode
		if (modeType.equals(ModeType.UPDATE.getModeType()) && riskDTO.getId() == null) {
			errorList.add(
					new MalformedRequestErrorDetails("id", "Risk Id is mandatory.", "Please provide valid risk Id."));
		}

		// Risk name is mandatory
		checkStringFieldNull("name", riskDTO.getName(), "Name", errorList);

		// Risk Description is mandatory
		checkStringFieldNull("description", riskDTO.getDescription(), "Description", errorList);

		return errorList;
	}

	private static void checkStringFieldNull(String requestFieldName, String fieldValue, String userReadableFieldName,
			List<MalformedRequestErrorDetails> errorList) {
		if (StringUtils.isBlank(fieldValue)) {
			errorList.add(new MalformedRequestErrorDetails(requestFieldName, userReadableFieldName + " is mandatory",
					"Please provide a valid " + userReadableFieldName.toLowerCase()));
		}
	}
}
