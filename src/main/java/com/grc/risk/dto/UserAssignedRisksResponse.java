package com.grc.risk.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class UserAssignedRisksResponse {
	private Long mappingId;
	private String riskName;
	private String riskDescription;
	private List<RiskAssessmentDTO> assessments;
	private boolean isAssessmentAvailable;

	public Long getMappingId() {
		return mappingId;
	}

	public void setMappingId(Long mappingId) {
		this.mappingId = mappingId;
	}

	public String getRiskName() {
		return riskName;
	}

	public void setRiskName(String riskName) {
		this.riskName = riskName;
	}

	public String getRiskDescription() {
		return riskDescription;
	}

	public void setRiskDescription(String riskDescription) {
		this.riskDescription = riskDescription;
	}

	public List<RiskAssessmentDTO> getAssessments() {
		return assessments;
	}

	public void setAssessments(List<RiskAssessmentDTO> assessments) {
		this.assessments = assessments;
	}

	public boolean isAssessmentAvailable() {
		return isAssessmentAvailable;
	}

	public void setAssessmentAvailable(boolean isAssessmentAvailable) {
		this.isAssessmentAvailable = isAssessmentAvailable;
	}

}
