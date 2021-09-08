package com.grc.risk.dto;

import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class RiskAssessmentDTO {

	private Long id;
	private Long assessmentScore;
	private String comment;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Timestamp assessedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Timestamp getAssessedDate() {
		return assessedDate;
	}

	public void setAssessedDate(Timestamp assessedDate) {
		this.assessedDate = assessedDate;
	}
}
