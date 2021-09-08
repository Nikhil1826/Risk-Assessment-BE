package com.grc.risk.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class AssessmentRequest {

	private Long mappingId;
	private Long assessmentScore;
	private String comment;

	public Long getMappingId() {
		return mappingId;
	}

	public void setMappingId(Long mappingId) {
		this.mappingId = mappingId;
	}

	public Long getAssessmentScore() {
		return assessmentScore;
	}

	public void setAssessmentScore(Long assessmentScore) {
		this.assessmentScore = assessmentScore;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
