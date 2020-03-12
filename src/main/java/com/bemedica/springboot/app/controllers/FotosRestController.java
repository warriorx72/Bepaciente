package com.bemedica.springboot.app.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import com.bemedica.springboot.app.controllers.UserController;
import com.bemedica.springboot.app.dto.MedicoPersonaDTO;
import com.bemedica.springboot.app.models.entity.Medico;
import com.bemedica.springboot.app.models.entity.Persona;
import com.bemedica.springboot.app.service.MedicoService;
import com.bemedica.springboot.app.service.PersonaService;
import com.bemedica.springboot.app.service.UserService;

public class FotosRestController {
	@Autowired
	private MedicoService medicoService;
	@Autowired
	private PersonaService personaService;
	@Autowired
	private UserService userService;

	@GetMapping(value = "/fotos")
	public List<MedicoPersonaDTO> getMedicos(HttpServletRequest request) {
		Medico medicos = new Medico();
		Persona persona = new Persona();
		UserController user = new UserController();

		medicos = medicoService.findOne(user.UsuarioDoctor(request, userService).longValue());
		persona = personaService.findOne(medicos.getPersona().getId());
		
		return medicoService.findAllById(medicos.getMedico_id());
	}
}
