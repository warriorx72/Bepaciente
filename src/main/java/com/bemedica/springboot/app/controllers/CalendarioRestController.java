package com.bemedica.springboot.app.controllers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bemedica.springboot.app.models.entity.Calendario;
import com.bemedica.springboot.app.models.entity.Direccion;
import com.bemedica.springboot.app.models.entity.Medico;
import com.bemedica.springboot.app.models.entity.Persona;
import com.bemedica.springboot.app.models.entity.User;
import com.bemedica.springboot.app.service.CalendarioService;
import com.bemedica.springboot.app.service.DireccionService;
import com.bemedica.springboot.app.service.MedicoService;
import com.bemedica.springboot.app.service.PersonaService;
import com.bemedica.springboot.app.service.UserService;

@RestController
public class CalendarioRestController 
{
	@Autowired
	private CalendarioService calendarioService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MedicoService medicoService;
	
	@Autowired
	private DireccionService direccionService;
	
	@Autowired
	private PersonaService personaService;
	
	@Autowired
    public JavaMailSender emailSender;
	
	@RequestMapping(value="/allevents", method=RequestMethod.GET)
	public List<Calendario> allEvents() {
		return calendarioService.findAll();
	}
	
	@RequestMapping(value="/findCitas", method= RequestMethod.GET)
	public String citas(@RequestParam Long id) throws JSONException {
		
		List<Calendario> citas = calendarioService.findByidmedico(id);
		JSONObject citasFinal = new JSONObject();
		JSONArray enviar = new JSONArray();
		
		for (Object record : citas) {
		    Object[] fields = (Object[]) record;
		    citasFinal = null;
		    citasFinal = new JSONObject();
		    
		    citasFinal.put("id", fields[0]);
		    citasFinal.put("start", fields[1]);
		    citasFinal.put("end", fields[2]);
		    citasFinal.put("color", fields[3]);
		    citasFinal.put("title", fields[4]);
		    citasFinal.put("description", fields[5]);
		    
		    enviar.put(citasFinal);
		}
		
		enviar.put(calendarioService.getStart(id));
		enviar.put(calendarioService.getEnd(id));
		
		
		return enviar.toString();
	}
	
	@RequestMapping(value="/buscar_doctor_info", method= RequestMethod.GET)
	public String med_info(@RequestParam Long id) throws JSONException {
		
		Medico med = medicoService.findOne(id);
		
		JSONObject obj_med = new JSONObject();
		JSONArray enviar = new JSONArray();
		
		    
		obj_med.put("id", med.getMedico_id());
		obj_med.put("nombre", med.getPersona().getPersonaNombre());
		obj_med.put("pat", med.getPersona().getPersonaAP());
		obj_med.put("mat", med.getPersona().getPersonaAM());
		obj_med.put("espe", med.getMedico_especialidad());
		obj_med.put("concon", med.getControl_consulta());
		obj_med.put("pricon", med.getPrimer_consulta());
		obj_med.put("domcon", med.getDomicilio_consulta());
		obj_med.put("tel", med.getPersona().getPersnaTelCel());
		obj_med.put("idper", med.getPersona().getId());
		obj_med.put("idmed", med.getMedico_id());
		
		Persona per = personaService.findOne(med.getPersona().getId());
		Direccion dir = direccionService.findOne(Long.valueOf(per.getDireccion()));
		
		obj_med.put("id", dir.getDireccion_id());
		obj_med.put("calle", dir.getDireccion_calle());
		obj_med.put("colonia", dir.getDireccion_colonia());
		obj_med.put("municipio", dir.getDireccion_municipio());
		obj_med.put("interno", dir.getDireccion_num_inter());
		obj_med.put("externo", dir.getDireccion_numero_ext());
		obj_med.put("cp", dir.getDireccion_postal());
		obj_med.put("estado", dir.getDireccion_estado());
		
		
		return obj_med.toString();
	}
	
	@RequestMapping(value="/event", method=RequestMethod.POST)
	public Calendario addEvent(@RequestBody Calendario calendario) {
		calendarioService.save(calendario);
		
		return calendario; 
	}
	
	@RequestMapping(value="/EnvioMail", method=RequestMethod.POST)
	public Calendario sendMail(@RequestBody Calendario calendario) throws Exception {
		
		User u = userService.getUserById(calendario.getIdpaciente());
		
		SimpleMailMessage message = new SimpleMailMessage();
		
		String[] valores = new String[7];
		
		Medico med = new Medico();
		med = medicoService.findOne((long) calendario.getIdmedico());
		Persona perso = med.getPersona();
		Direccion dir = direccionService.findOne(Long.valueOf(perso.getDireccion()));
		
		
		if(med.getPersona().getPersonaNombre() != null)
		{
			valores[0] = med.getPersona().getPersonaNombre();
		}
		else
		{
			valores[0] = "Nombre Desconocido";
		}
		
		if(med.getPersona().getPersonaAP() != null)
		{
			valores[1] = med.getPersona().getPersonaAP();
		}
		else
		{
			valores[1] = "Apellido Paterno Desconocido";
		}
		
		if(med.getPersona().getPersonaAM() != null)
		{
			valores[2] = med.getPersona().getPersonaAM();
		}
		else
		{
			valores[2] = "Apellido Materno Desconocido";
		}
		
		if(med.getMedico_cedula() != null)
		{
			valores[3] = med.getMedico_cedula();
		}
		else
		{
			valores[3] = "Cédula Desconocida";
		}
		
		if(med.getMedico_especialidad() != null)
		{
			valores[4] = med.getMedico_especialidad();
		}
		else
		{
			valores[4] = "Especialidad Desconocida";
		}
		
		if(dir.getDireccion_calle() != null && dir.getDireccion_numero_ext() != null && dir.getDireccion_colonia() != null && dir.getDireccion_municipio() != null && dir.getDireccion_estado() != null && dir.getDireccion_postal() != null)
		{
			valores[5] = "Calle: " + dir.getDireccion_calle() + " #" + dir.getDireccion_numero_ext() + ". Colonia: " + dir.getDireccion_colonia() + ". " + dir.getDireccion_municipio() + ", " + dir.getDireccion_estado() + ". CP: " + dir.getDireccion_postal();
		}
		else
		{
			valores[5] = "Dirección Desconocida";
		}
		
		if(med.getPrimer_consulta() != null)
		{
			valores[6] = "$" + med.getPrimer_consulta();
		}
		else
		{
			valores[6] = "Costo Desconocido";
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("Tu cita tomará lugar a las: " + calendario.getStart().substring(11, 16));
		sb.append(System.lineSeparator());
		sb.append("El día " + calendario.getStart().substring(5, 7) + " del mes " + calendario.getStart().substring(5, 7) + " del presente año. ");
		sb.append(System.lineSeparator());
		sb.append("Ubicación: " + valores[5]);
		sb.append(System.lineSeparator());
		sb.append("Con el Médico:");
		sb.append(System.lineSeparator());
		sb.append("Nombre: " + valores[0] + " " + valores[1] + " " + valores[2]);
		sb.append(System.lineSeparator());
		sb.append("Especialidad: " + valores[4] + " .Cédula: " + valores[3]);
		message.setTo(u.getEmail());
        message.setSubject("Confirmación de Cita");
        message.setText(sb.toString());
 
        // Send Message!
        this.emailSender.send(message);
		
		return calendario; 
	}
	
	@RequestMapping(value="/getMedico", method=RequestMethod.GET)
	public String[] getMedico(@RequestParam(name = "id") Long id) {
		
		String[] valores = new String[7];
		System.out.println(id);
		
		Medico med = new Medico();
		Direccion dir = new Direccion();
		med = medicoService.findOne(id);
		System.out.println(med.getMedico_id());
		dir = direccionService.findOne(Long.valueOf(med.getDomicilio_consulta()));
		System.out.println(med.getDomicilio_consulta());
		
		
		if(med.getPersona().getPersonaNombre() != null)
		{
			valores[0] = med.getPersona().getPersonaNombre();
		}
		else
		{
			valores[0] = "Nombre Desconocido";
		}
		
		if(med.getPersona().getPersonaAP() != null)
		{
			valores[1] = med.getPersona().getPersonaAP();
		}
		else
		{
			valores[1] = "Apellido Paterno Desconocido";
		}
		
		if(med.getPersona().getPersonaAM() != null)
		{
			valores[2] = med.getPersona().getPersonaAM();
		}
		else
		{
			valores[2] = "Apellido Materno Desconocido";
		}
		
		if(med.getMedico_cedula() != null)
		{
			valores[3] = med.getMedico_cedula();
		}
		else
		{
			valores[3] = "Cédula Desconocida";
		}
		
		if(med.getMedico_especialidad() != null)
		{
			valores[4] = med.getMedico_especialidad();
		}
		else
		{
			valores[4] = "Especialidad Desconocida";
		}
		
		if(dir.getDireccion_calle() != null && dir.getDireccion_numero_ext() != null && dir.getDireccion_colonia() != null && dir.getDireccion_municipio() != null && dir.getDireccion_estado() != null && dir.getDireccion_postal() != null)
		{
			valores[5] = "Calle: " + dir.getDireccion_calle() + " #" + dir.getDireccion_numero_ext() + ". Colonia: " + dir.getDireccion_colonia() + ". " + dir.getDireccion_municipio() + ", " + dir.getDireccion_estado() + ". CP: " + dir.getDireccion_postal();
		}
		else
		{
			valores[5] = "Dirección Desconocida";
		}
		
		if(med.getPrimer_consulta() != null)
		{
			valores[6] = "$" + med.getPrimer_consulta();
		}
		else
		{
			valores[6] = "Costo Desconocido";
		}

		
		return valores;
	}
	
	
	@RequestMapping(value="/event", method=RequestMethod.PATCH)
	public Calendario updateEvent(@RequestBody Calendario calendario) {
		calendario.setStatus(0);
		calendarioService.save(calendario);
		return calendario;
	}
	@RequestMapping(value="/event", method=RequestMethod.DELETE)
	public void removeEvent(@RequestParam(name = "id") Long id) {
		calendarioService.delete(id);
	}
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	class BadDateFormatException extends RuntimeException {
	  private static final long serialVersionUID = 1L;

		public BadDateFormatException(String dateString) {
	        super(dateString);
	    }
	}
}
