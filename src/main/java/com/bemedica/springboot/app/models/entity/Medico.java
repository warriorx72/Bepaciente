package com.bemedica.springboot.app.models.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.bemedica.springboot.app.models.entity.Paciente;
import com.bemedica.springboot.app.models.entity.Persona;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "app_medicos")
public class Medico implements Serializable
{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long medico_id;
	
	@Column
	@JsonIgnore
	private String medico_especialidad;
	
	@Column
	@JsonIgnore
	private String medico_cedula;

	@Column
	@JsonIgnore
	private String primer_consulta;

	@Column
	@JsonIgnore
	private String control_consulta;
	
	@Column
	@JsonIgnore
	String domicilio_consulta;

	@Column
	private String medico_foto;
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "persona_id", referencedColumnName = "persona_id")
	private Persona persona;

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "app_medico_paciente", joinColumns = @JoinColumn(name = "medico_id", referencedColumnName = "medico_id"), inverseJoinColumns = @JoinColumn(name = "paciente_id", referencedColumnName = "paciente_id"))
	private Set<Paciente> paciente = new HashSet<>();

	public Set<Paciente> getPaciente() {
		return paciente;
	}

	public void setPaciente(Set<Paciente> paciente) {
		this.paciente = paciente;
	}

	public Long getMedico_id() {
		return medico_id;
	}

	public void setMedico_id(Long medico_id) {
		this.medico_id = medico_id;
	}

	public String getMedico_especialidad() {
		return medico_especialidad;
	}

	public void setMedico_especialidad(String medico_especialidad) {
		this.medico_especialidad = medico_especialidad;
	}
	
	public String getMedico_cedula() {
		return medico_cedula;
	}

	public void setMedico_cedula(String medico_cedula) {
		this.medico_cedula = medico_cedula;
	}

	public String getPrimer_consulta() {
		return primer_consulta;
	}

	public void setPrimer_consulta(String primer_consulta) {
		this.primer_consulta = primer_consulta;
	}

	public String getControl_consulta() {
		return control_consulta;
	}

	public void setControl_consulta(String control_consulta) {
		this.control_consulta = control_consulta;
	}

	public String getDomicilio_consulta() {
		return domicilio_consulta;
	}

	public void setDomicilio_consulta(String domicilio_consulta) {
		this.domicilio_consulta = domicilio_consulta;
	}

	public String getMedico_foto() {
		return medico_foto;
	}

	public void setMedico_foto(String medico_foto) {
		this.medico_foto = medico_foto;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
