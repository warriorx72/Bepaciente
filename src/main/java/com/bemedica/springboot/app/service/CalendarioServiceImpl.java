package com.bemedica.springboot.app.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bemedica.springboot.app.models.entity.Calendario;
import com.bemedica.springboot.app.repository.CalendarioRepository;

@Service
public class CalendarioServiceImpl implements CalendarioService
{
	@Autowired
	private CalendarioRepository repository;
	
	@Autowired
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public List<Calendario> findAll(){
		//return (List<Calendario>) repository.findAll();
		return (List<Calendario>) em.createNativeQuery("call citas_medico();").getResultList();
	}

	@Override
	@Transactional
	public void save(Calendario calendario) {
		repository.save(calendario);
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Calendario findOne(Long id) {
		return repository.findById(id).orElse(null);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public List<Calendario> findByDateBetween() {
		// TODO Auto-generated method stub
		return em.createQuery("SELECT c.inicio,c.fin FROM Calendario c").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true)
	public List<Calendario> findByidmedico(Long id) {
		return em.createQuery("SELECT c.id, c.start, c.end, c.color, c.title, c.description, c.idmedico, c.idpaciente FROM Calendario c WHERE idmedico =" + id + " AND status = 1").getResultList();
	}

	@Override
	public String getStart(Long id) {
		return (String) em.createQuery("SELECT MIN(CONCAT(DATE_FORMAT(c.start, '%H'),':',DATE_FORMAT(c.start, '%i')))  from Calendario c where id_medico =" + id + " AND status = 1").getSingleResult();
	}

	@Override
	public String getEnd(Long id) {
		return (String) em.createQuery("SELECT MAX(CONCAT(DATE_FORMAT(c.end, '%H'),':',DATE_FORMAT(c.end, '%i')))  from Calendario c where id_medico =" + id + " AND status = 1").getSingleResult();
	}

}
