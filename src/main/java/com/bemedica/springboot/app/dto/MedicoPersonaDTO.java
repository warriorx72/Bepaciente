package com.bemedica.springboot.app.dto;

public class MedicoPersonaDTO {
	private Long medico_id;
	private String medico_foto;
	private Long persona_id;
	private String persona_nombre;
	private String persona_ap;
	
	public Long getMedico_id() {
		return medico_id;
	}
	public void setMedico_id(Long medico_id) {
		this.medico_id = medico_id;
	}
	public String getMedico_foto() {
		return medico_foto;
	}
	public void setMedico_foto(String medico_foto) {
		this.medico_foto = medico_foto;
	}
	public Long getPersona_id() {
		return persona_id;
	}
	public void setPersona_id(Long persona_id) {
		this.persona_id = persona_id;
	}
	public String getPersona_nombre() {
		return persona_nombre;
	}
	public void setPersona_nombre(String persona_nombre) {
		this.persona_nombre = persona_nombre;
	}
	public String getPersona_ap() {
		return persona_ap;
	}
	public void setPersona_ap(String persona_ap) {
		this.persona_ap = persona_ap;
	}
	
	public MedicoPersonaDTO(Long medico_id, String medico_foto, Long persona_id, String persona_nombre, String persona_ap) {
		super();
		this.medico_id = medico_id;
		this.medico_foto = medico_foto;
		this.persona_id = persona_id;
		this.persona_nombre = persona_nombre;
		this.persona_ap = persona_ap;
	}
}
