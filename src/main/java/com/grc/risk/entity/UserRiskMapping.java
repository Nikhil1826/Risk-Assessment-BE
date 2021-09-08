package com.grc.risk.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "USER_RISK_MAPPING", uniqueConstraints = @UniqueConstraint(columnNames = { "USER_ID", "RISK_ID" }))
public class UserRiskMapping implements Serializable {
	private static final long serialVersionUID = -5114201612110795472L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MAPPING_ID")
	private Long mappingId;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	@ManyToOne
	@JoinColumn(name = "RISK_ID")
	private Risk risk;

	@OneToMany(mappedBy = "userRiskMapping", cascade = CascadeType.REMOVE)
	private List<RiskAssessment> riskAssessmentList;

	public Long getMappingId() {
		return mappingId;
	}

	public void setMappingId(Long mappingId) {
		this.mappingId = mappingId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Risk getRisk() {
		return risk;
	}

	public void setRisk(Risk risk) {
		this.risk = risk;
	}

	public List<RiskAssessment> getRiskAssessmentList() {
		return riskAssessmentList;
	}

	public void setRiskAssessmentList(List<RiskAssessment> riskAssessmentList) {
		this.riskAssessmentList = riskAssessmentList;
	}

}
