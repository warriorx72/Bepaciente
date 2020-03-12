package com.bemedica.springboot.app.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bemedica.springboot.app.service.MedicoService;
import com.bemedica.springboot.app.dto.MedicoPersonaDTO;
import com.bemedica.springboot.app.models.entity.Medico;
import com.bemedica.springboot.app.repository.MedicoRepository;

@Service
public class MedicoServiceImpl implements MedicoService
{
	@Autowired
	private EntityManager em;
	@Autowired
	private MedicoRepository repository;
	
	@Override
	@Transactional(readOnly=true)
	public List<Medico> findAll() {
		// TODO Auto-generated method stub
		return (List<Medico>) repository.findAll();
	}

	@Override
	@Transactional
	public void save(Medico medico) {
		// TODO Auto-generated method stub
		repository.save(medico);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Medico findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Medico> getLocacion(String especialidad, String estado) {
		return em.createNativeQuery("call locacion_medico('" + especialidad + "','" + estado + "');").getResultList();
	}

	@Override
	public List<MedicoPersonaDTO> findAllById(Long id) {
		// TODO Auto-generated method stub
		return em.createQuery("SELECT distinct	new com.bemedicos.springboot.app.dto.MedicoPersonaDTO (m.medico_id, m.medico_foto, m.persona.persona_id, m.persona.persona_nombre, m.persona.persona_ap) FROM Medicos m WHERE m.medico_id =" + id, MedicoPersonaDTO.class ).getResultList();
	}

}
