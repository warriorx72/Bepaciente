package com.bemedica.springboot.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bemedica.springboot.app.service.DireccionService;
import com.bemedica.springboot.app.models.entity.Direccion;
import com.bemedica.springboot.app.repository.DireccionRepository;

@Service
public class DireccionServiceImpl implements DireccionService
{
	@Autowired
	private DireccionRepository repository;
	
	@Override
	@Transactional(readOnly=true)
	public List<Direccion> findAll() {
		// TODO Auto-generated method stub
		return (List<Direccion>) repository.findAll();
	}

	@Override
	@Transactional
	public void save(Direccion direccion) {
		// TODO Auto-generated method stub
		repository.save(direccion);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Direccion findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}
}
