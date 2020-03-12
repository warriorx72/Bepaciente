package com.bemedica.springboot.app.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bemedica.springboot.app.models.entity.CodigoConfirmacion;
import com.bemedica.springboot.app.repository.CodigoConfirmacionRepository;

public class CodigoConfirmacionServiceImpl implements CodigoConfirmacionService
{
	@Autowired
	private CodigoConfirmacionRepository repository;
	
	@Override
	public void save(CodigoConfirmacion CodigoConfirmacion) {
		// TODO Auto-generated method stub
		repository.save(CodigoConfirmacion);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	public CodigoConfirmacion findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	@Override
	public CodigoConfirmacion findByCodigo(String token) {
		// TODO Auto-generated method stub
		return repository.findByCodigo(token);
	}

	@Override
	public CodigoConfirmacion findByusermed(int id) {
		// TODO Auto-generated method stub
		return repository.findByusermed(id);
	}
}
