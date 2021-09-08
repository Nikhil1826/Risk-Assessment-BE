package com.grc.risk.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnTransformer;

@Entity
@Table(name = "USER")
public class User implements Serializable {
	private static final long serialVersionUID = -4540273677580138307L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "USERNAME")
	private String username;

	@ColumnTransformer(write = "HEX(AES_ENCRYPT(?, 'SECRET KEY'))", read = "AES_DECRYPT(UNHEX(password), 'SECRET KEY')")
	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "IS_ADMIN")
	private boolean isAdmin;

	@OneToMany(mappedBy = "user")
	private List<UserRiskMapping> userRiskMappings;

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public List<UserRiskMapping> getUserRiskMappings() {
		return userRiskMappings;
	}

	public void setUserRiskMappings(List<UserRiskMapping> userRiskMappings) {
		this.userRiskMappings = userRiskMappings;
	}

}
