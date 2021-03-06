package com.grc.risk.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "RISK")
public class Risk implements Serializable {
	private static final long serialVersionUID = -6492995387802227152L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@OneToMany(mappedBy = "risk", cascade = CascadeType.REMOVE)
	private List<UserRiskMapping> userRiskMappings;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<UserRiskMapping> getUserRiskMappings() {
		return userRiskMappings;
	}

	public void setUserRiskMappings(List<UserRiskMapping> userRiskMappings) {
		this.userRiskMappings = userRiskMappings;
	}

}
