package com.bemedica.springboot.app.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="user_pac")
public class User implements Serializable
{
	private static final long serialVersionUID = -6833167247955613395L;

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private Long id;
	
	@Column(name="paciente_id")
	private String PacienteId;
	
	@Column(name="first_name") 
	@NotBlank
	private String FirstName;
	
	@Column(name="last_name") 
	@NotBlank
	private String LastName;
	
	@Column(name="apmat") 
	@NotBlank
	private String apmat;
	
	public String getApmat() {
		return apmat;
	}

	public void setApmat(String apmat) {
		this.apmat = apmat;
	}

	@Column(name="email",unique = true) 
	@NotBlank
	private String email;
	
	@Column(name="username") 
	@NotBlank
	private String username;
	
	@Column(name="password") 
	private String Password;
	
	@Column(name="status")
	private int Status;
	
	@Transient 
	private String confirmPassword;
	
	public User() 
	{	
		super();
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + FirstName + ", lastName=" + LastName + ", email=" + email
				+ ", username=" + username + ", password=" + Password + "]";
	}
	
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPacienteId() {
		return PacienteId;
	}

	public void setPacienteId(String pacienteId) {
		this.PacienteId = pacienteId;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		this.FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		this.LastName = lastName;
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
		return Password;
	}

	public void setPassword(String password) {
		this.Password = password;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		this.Status = status;
	}
}
