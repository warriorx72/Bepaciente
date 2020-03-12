package com.bemedica.springboot.app.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bemedica.springboot.app.models.entity.Medico;
import com.bemedica.springboot.app.service.MedicoService;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

@Controller
public class LocacionController 
{
	@Autowired
	private MedicoService medicoService;
	
	@RequestMapping(value = "/buscar_doctor")
	public @ResponseBody List<Medico> BusquedaDoctor(@RequestParam(value = "estado") String Estado, @RequestParam(value = "especialidad") String Especialidad, HttpServletRequest request, Model model) throws MessagingException
	{
		return medicoService.getLocacion(Especialidad, Estado);
	}
	
	@RequestMapping(value = "/prueba")
	public String BusquedaDoctor() 
	{
		JSONObject jsonObject = new JSONObject();
		return "index";
	}
}
