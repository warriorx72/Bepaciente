package com.bemedica.springboot.app.controllers;

import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bemedica.springboot.app.service.CalendarioService;
import com.bemedica.springboot.app.service.UploadFileService;

@Controller
public class CalendarioController 
{
	
	@Autowired
	CalendarioService calendarioService;

	@Autowired
	UploadFileService uFileService;
	
	@GetMapping(value="/agenda")
	public String agenda(HttpServletRequest request, Model model) {
		return "calendario";
	}
	
	@GetMapping(value = "/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

		Resource recurso = null;
		try {
			recurso = uFileService.load(filename);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}
}
