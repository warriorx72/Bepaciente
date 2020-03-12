package com.bemedica.springboot.app.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bemedica.springboot.app.repository.CodigoConfirmacionRepository;
import com.bemedica.springboot.app.repository.UserRepository;
import com.bemedica.springboot.app.service.UserService;
import com.bemedica.springboot.app.models.entity.CodigoConfirmacion;
import com.bemedica.springboot.app.models.entity.User;

@Controller
public class CorreoController 
{
	@Autowired
	CodigoConfirmacionRepository CodigoConfirmacionDao;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;
	
    @Autowired
    public JavaMailSender emailSender;
    
    @RequestMapping(value="/confirmar-correo", method= {RequestMethod.GET, RequestMethod.POST})
	public String ConfirmarCorreo(@RequestParam("token")String ConfirmationToken) throws Exception 
	{
		//Crear y llenar el objeto CodigoConfirmacion con el parametro recibido
		CodigoConfirmacion ct = CodigoConfirmacionDao.findByCodigo(ConfirmationToken);
		
		if(ct != null)
		{
			//Buscar al usuario en base a su ID y actualizar su estatus
			User u = userService.getUserById(Long.valueOf(ct.getusermed()));
			u.setStatus(1);
			userService.updateUser(u);
			
			//Se borra el codigo en la tabla de CodigoConfirmacion 
			CodigoConfirmacionDao.delete(ct);
			return "redirect:/index?Confirmed";
		}
		else
		{
			//No coincide el codigo
			return "redirect:/index?NoCode";
		}

	}
	
	@RequestMapping(value="/Reenviar_Correo", method= {RequestMethod.GET})
	public String ReenviarCorreo(@ModelAttribute("User") User usuario, BindingResult result)
	{
		//Primero se obtiene al usuario en base a su correo
		Optional<User> u = userRepository.findByEmail(usuario.getEmail());
		
		//Se pregunta si existe o no
		if(u.toString() == "Optional.empty")
		{
			//Si no existe, se regresa al Login con la advertencia de que no existe el usuario
			return "redirect:/index?NoUser";
		}
		else 
		{	
			//Si existe, se envia el correo correspondiente
			//Se pasa el modelo del form a un nuevo objeto
			User user = u.get();
			// Se crea el Mail
	        SimpleMailMessage message = new SimpleMailMessage();
	        
	        //Se genera el codigo
	        UUID uuid = UUID.randomUUID();
	        String randomUUIDString = uuid.toString();
	        
	        //Se crea un nuevo objeto para el CodigoConfirmacion
			CodigoConfirmacion cc = new CodigoConfirmacion();
			
			//Se almacenan los parametros en el objeto previamente creado
			cc.setCodigo(randomUUIDString);
			cc.setusermed(user.getId().intValue());
			
			//Se declara y se borra el codigo anterior si es que existe.
			CodigoConfirmacion delete = CodigoConfirmacionDao.findByusermed(user.getId().intValue());
			if(delete != null)
			{
				CodigoConfirmacionDao.delete(delete);
			}
			
			//Se guarda el nuevo.
			CodigoConfirmacionDao.save(cc);
	        
	        //Se envia el correo con la url de confirmacions
	        message.setTo(user.getEmail());
	        message.setSubject("Bienvenido a Bemédia: " + user.getFirstName() + " " + user.getLastName());
	        message.setText("Hola, Se ha enviado un nuevo correo, da clic al enlace para confirmar tu email: " 
	        + "http://localhost:8080/confirmar-correo?token=" + randomUUIDString );
	 
	        // Send Message!
	        this.emailSender.send(message);
	        
	        return "redirect:/index?EmailSended";
		}
	}
}
