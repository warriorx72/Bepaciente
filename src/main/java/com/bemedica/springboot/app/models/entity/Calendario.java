package com.bemedica.springboot.app.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="app_calendario")
public class Calendario 
{
	@Id
	@Column(name="calendario_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="inicio")
	private String start;
	
	@Column(name="fin")
	private String end;
	
	@Column(name="color")
	private String color;
	
	@Column(name="titulo")
	private String title;
	
	@Column(name="descripcion")
	private String description;
	
	@Column(name="id_medico")
	private int idmedico;
	
	@Column(name="id_paciente")
	private Long idpaciente;
	
	@Column(name="status")
	private int status;
	

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIdmedico() {
		return idmedico;
	}

	public void setId_medico(int idmedico) {
		this.idmedico = idmedico;
	}

	public Long getIdpaciente() {
		return idpaciente;
	}

	public void setId_paciente(Long idpaciente) {
		this.idpaciente = idpaciente;
	}
}
