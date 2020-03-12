package com.bemedica.springboot.app.service;

import java.util.List;

import com.bemedica.springboot.app.models.entity.Paciente;

public interface PacienteService 
{
	public List<Paciente> findAll();
	
	public void save(Paciente paciente);
	
	public void delete(Long id);
	
	public Paciente findOne(Long id);
}
