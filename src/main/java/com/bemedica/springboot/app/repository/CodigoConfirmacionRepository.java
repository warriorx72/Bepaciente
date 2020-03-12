package com.bemedica.springboot.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.bemedica.springboot.app.models.entity.CodigoConfirmacion;

public interface CodigoConfirmacionRepository extends CrudRepository<CodigoConfirmacion, Long>
{
	public CodigoConfirmacion findByCodigo(String token);
	
	public CodigoConfirmacion findByusermed(int id);
}
