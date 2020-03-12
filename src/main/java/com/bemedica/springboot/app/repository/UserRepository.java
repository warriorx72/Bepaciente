package com.bemedica.springboot.app.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bemedica.springboot.app.models.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>
{
	public Optional<User> findByEmail(String email);
	public Optional<User> findByUsername(String username);
}
