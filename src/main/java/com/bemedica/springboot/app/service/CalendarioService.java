package com.bemedica.springboot.app.service;

import java.util.List;

import com.bemedica.springboot.app.models.entity.Calendario;

public interface CalendarioService 
{
	public List<Calendario> findAll();
	
	public void save(Calendario calendario);
	
	public void delete(Long id);
	
	public Calendario findOne(Long id);

	public List<Calendario> findByDateBetween();
	
	public List<Calendario> findByidmedico(Long id);
	
	public String getStart(Long id);
	
	public String getEnd(Long id);
}
