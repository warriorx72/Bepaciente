package com.bemedica.springboot.app.service;

import com.bemedica.springboot.app.models.entity.CodigoConfirmacion;

public interface CodigoConfirmacionService 
{
	public void save(CodigoConfirmacion CodigoConfirmacion);
	
	public void delete(Long id);
	
	public CodigoConfirmacion findOne(Long id);
	
	public CodigoConfirmacion findByCodigo(String token);
	
	public CodigoConfirmacion findByusermed(int id);
}
