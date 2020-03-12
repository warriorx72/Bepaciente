package com.bemedica.springboot.app.service;

import java.util.List;

import com.bemedica.springboot.app.models.entity.Medico;
import com.bemedica.springboot.app.dto.MedicoPersonaDTO;

public interface MedicoService 
{
	public List<Medico> findAll();
	
	public Medico findOne(Long MedicosId);
	
	public void save(Medico medicos);
	
	public void delete(Long id);
	
	public List<Medico> getLocacion(String especialidad, String estado);
	
	public List<MedicoPersonaDTO> findAllById(Long id);
}
