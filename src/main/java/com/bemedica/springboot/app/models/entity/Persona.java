package com.bemedica.springboot.app.models.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.bemedica.springboot.app.models.entity.Direccion;
import com.bemedica.springboot.app.models.entity.Medico;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="persona")
public class Persona implements Serializable
{

	//
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="persona_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="persona_nombre")
	private String personaNombre;
	
	@Column(name="persona_ap")
	private String personaAP;
	
	@Column(name="persona_am")
	private String personaAM;
	
	@JsonIgnore
	@Column(name="persona_fecha_na")
	private String personaFechaNac;

	@JsonIgnore
	@Column(name="persona_rfc")
	private String personaRFC;
	
	@JsonIgnore
	@Column(name="persona_genero")
	private String personaGenero;
	
	@Column(name="persona_email")
	private String personaEmail;
	
	@JsonIgnore
	@Column(name="persona_tel_casa")
	private String personaTelCasa;
	
	@JsonIgnore
	@Column(name="persona_tel_cel")
	private String persnaTelCel;
	
	@JsonIgnore
	@Column(name="persona_tel_oficina")
	private String personaTelOficina;
	
	@JsonIgnore
	@Column(name="persona_tel_exten")
	private String personaTelExt;
	
	@JsonIgnore
	@Column(name="id_direccion")
	private String direccion;

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPersonaNombre() {
		return personaNombre;
	}

	public void setPersonaNombre(String personaNombre) {
		this.personaNombre = personaNombre;
	}

	public String getPersonaAP() {
		return personaAP;
	}

	public void setPersonaAP(String personaAP) {
		this.personaAP = personaAP;
	}

	public String getPersonaAM() {
		return personaAM;
	}

	public void setPersonaAM(String personaAM) {
		this.personaAM = personaAM;
	}

	public String getPersonaFechaNac() {
		return personaFechaNac;
	}

	public void setPersonaFechaNac(String personaFechaNac) {
		this.personaFechaNac = personaFechaNac;
	}

	public String getPersonaRFC() {
		return personaRFC;
	}

	public void setPersonaRFC(String personaRFC) {
		this.personaRFC = personaRFC;
	}

	public String getPersonaGenero() {
		return personaGenero;
	}

	public void setPersonaGenero(String personaGenero) {
		this.personaGenero = personaGenero;
	}

	public String getPersonaEmail() {
		return personaEmail;
	}

	public void setPersonaEmail(String personaEmail) {
		this.personaEmail = personaEmail;
	}

	public String getPersonaTelCasa() {
		return personaTelCasa;
	}

	public void setPersonaTelCasa(String personaTelCasa) {
		this.personaTelCasa = personaTelCasa;
	}

	public String getPersnaTelCel() {
		return persnaTelCel;
	}

	public void setPersnaTelCel(String persnaTelCel) {
		this.persnaTelCel = persnaTelCel;
	}

	public String getPersonaTelOficina() {
		return personaTelOficina;
	}

	public void setPersonaTelOficina(String personaTelOficina) {
		this.personaTelOficina = personaTelOficina;
	}

	public String getPersonaTelExt() {
		return personaTelExt;
	}

	public void setPersonaTelExt(String personaTelExt) {
		this.personaTelExt = personaTelExt;
	}	
}
