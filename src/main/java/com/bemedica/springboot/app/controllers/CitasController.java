package com.bemedica.springboot.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CitasController {
	
	@GetMapping("/citas")
	public String citas(Model model)
	{
		return "citas";
	}
}
