package com.bemedica.springboot.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bemedica.springboot.app.models.entity.Medico;

@Repository
public interface MedicoRepository extends CrudRepository<Medico, Long>{

}
