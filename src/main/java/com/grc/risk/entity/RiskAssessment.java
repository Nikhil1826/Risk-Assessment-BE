package com.grc.risk.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "RISK_ASSESSMENT")
public class RiskAssessment implements Serializable {
	private static final long serialVersionUID = 31013756205293275L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ASSESSMENT_SCORE")
	private Long assessmentScore;

	@Column(name = "COMMENT")
	private String comment;

	@Column(name = "ASSESSED_DATE")
	private Timestamp assessedDate;

	@ManyToOne
	@JoinColumn(name = "USER_RISK_MAPPING_ID")
	private UserRiskMapping userRiskMapping;

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

	public UserRiskMapping getUserRiskMapping() {
		return userRiskMapping;
	}

	public void setUserRiskMapping(UserRiskMapping userRiskMapping) {
		this.userRiskMapping = userRiskMapping;
	}

}
