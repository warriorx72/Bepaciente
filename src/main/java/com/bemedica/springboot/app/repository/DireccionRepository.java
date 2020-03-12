package com.bemedica.springboot.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bemedica.springboot.app.models.entity.Direccion;

@Repository
public interface DireccionRepository extends CrudRepository<Direccion, Long>{

}
