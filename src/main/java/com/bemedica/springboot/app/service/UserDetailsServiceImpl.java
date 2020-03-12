package com.bemedica.springboot.app.service;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bemedica.springboot.app.repository.UserRepository;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService
{
	@Autowired
	UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		com.bemedica.springboot.app.models.entity.User appUser = repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Login Username Invalido."));

		int status = appUser.getStatus();
		
		if(status == 0 && status !=  1) 
		{
			System.out.println("El User Name es Invalido porque el Status es:" + status);
			throw new UsernameNotFoundException("Login Username Invalido.");
		}
		else 
		{
			System.out.println("Se cumplio la condición de Status");
		}
		
				Set grantList = new HashSet();				
				UserDetails user = (UserDetails) new User(username, appUser.getPassword(), grantList);
				return user;
	}
}
