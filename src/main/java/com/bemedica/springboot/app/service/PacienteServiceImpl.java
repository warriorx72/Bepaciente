package com.bemedica.springboot.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bemedica.springboot.app.models.entity.Paciente;
import com.bemedica.springboot.app.repository.PacienteRepository;
import com.bemedica.springboot.app.service.PacienteService;

@Service
public class PacienteServiceImpl implements PacienteService
{
	@Autowired
	private PacienteRepository repository;
	@Override
	@Transactional(readOnly=true)
	public List<Paciente> findAll() {
		// TODO Auto-generated method stub
		return (List<Paciente>) repository.findAll();
	}

	@Override
	@Transactional
	public void save(Paciente paciente) {
		// TODO Auto-generated method stub
		repository.save(paciente);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Paciente findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}
}
