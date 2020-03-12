package com.bemedica.springboot.app.service;

import java.util.List;

import com.bemedica.springboot.app.models.entity.Direccion;

public interface DireccionService 
{
	public List<Direccion> findAll();
	
	public void save(Direccion direccion);
	
	public void delete(Long id);
	
	public Direccion findOne(Long id);
}
