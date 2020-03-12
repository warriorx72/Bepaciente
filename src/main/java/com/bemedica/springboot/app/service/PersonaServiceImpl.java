package com.bemedica.springboot.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bemedica.springboot.app.models.entity.Persona;
import com.bemedica.springboot.app.repository.PersonaRepository;

@Service
public class PersonaServiceImpl implements PersonaService
{
	@Autowired
	private PersonaRepository repository;
	@Override
	@Transactional(readOnly=true)
	public List<Persona> findAll() {
		// TODO Auto-generated method stub
		return (List<Persona>) repository.findAll();
	}

	@Override
	@Transactional
	public void save(Persona persona) {
		// TODO Auto-generated method stub
		repository.save(persona);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Persona findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}
}
