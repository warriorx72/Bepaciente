package com.bemedica.springboot.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bemedica.springboot.app.models.entity.Paciente;

@Repository
public interface PacienteRepository extends CrudRepository<Paciente, Long>{

}
