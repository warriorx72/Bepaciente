package com.bemedica.springboot.app.service;

import java.util.List;

import com.bemedica.springboot.app.models.entity.Persona;

public interface PersonaService 
{
	public List<Persona> findAll();
	
	public void save(Persona persona);
	
	public void delete(Long id);
	
	public Persona findOne(Long id);
}
