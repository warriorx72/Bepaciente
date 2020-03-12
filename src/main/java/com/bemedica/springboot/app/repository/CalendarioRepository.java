package com.bemedica.springboot.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bemedica.springboot.app.models.entity.Calendario;

@Repository
public interface CalendarioRepository extends CrudRepository<Calendario, Long>{

	
}
