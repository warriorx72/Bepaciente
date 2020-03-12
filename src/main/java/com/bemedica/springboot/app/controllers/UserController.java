package com.bemedica.springboot.app.controllers;

import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.bemedica.springboot.app.repository.UserRepository;
import com.bemedica.springboot.app.service.UserService;
import com.bemedica.springboot.app.repository.CodigoConfirmacionRepository;
import com.bemedica.springboot.app.models.entity.CodigoConfirmacion;
import com.bemedica.springboot.app.dto.ChangePasswordForm;
import com.bemedica.springboot.app.models.entity.Persona;
import com.bemedica.springboot.app.models.entity.User;

@Controller
public class UserController 
{
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CodigoConfirmacionRepository CodigoConfirmacionDao;
	
	@Autowired
    public JavaMailSender emailSender;
	
	
	@PostMapping("/signup")
   	public String postSignup(@Valid @ModelAttribute("persona")Persona persona, @ModelAttribute("userForm")User user, BindingResult result, ModelMap model) throws Exception 
	{
		
		boolean flag = false;
		
		if(user.getPassword().equals(user.getConfirmPassword()))
		{
			//Las contraseñas si coinciden
			User Dummy = userService.createUser(user);
			
			if(Dummy.getFirstName() == "NickBusy")
			{
				//El nombre de usuario esta ocupado
				return "redirect:/index?NickBusy";
			}
			else
			{
				if(Dummy.getFirstName() == "EmailBusy")
				{
					//El correo ya esta ocupado
					return "redirect:/index?EmailBusy";
				}
				else
				{
					if(Dummy.getFirstName() == "EmailNickBusy")
					{
						//Ambos estan ocupados
						return "redirect:/index?EmailNickBusy";
					}
					else
					{
						flag = true;
					}
				}
			}
			
			if(flag)
			{
					// Se crea el Mail
			        SimpleMailMessage message = new SimpleMailMessage();
			        
					//Se genera el codigo
			        UUID uuid = UUID.randomUUID();
			        String randomUUIDString = uuid.toString();
			        
			        //Con el codigo generado se almacena el registro en la BD
					CodigoConfirmacion cc = new CodigoConfirmacion();
					
					cc.setCodigo(randomUUIDString);
					cc.setusermed(user.getId().intValue());
					
					CodigoConfirmacionDao.save(cc);
			        
			        
			        message.setTo(user.getEmail());
			        message.setSubject("Bienvenido a Bemédia: " + user.getFirstName() + " " + user.getLastName());
			        message.setText("Hola, Gracias por registrarte, da clic al enlace para confirmar tu email: " 
			        + "http://localhost:8080/confirmar-correo?token=" + randomUUIDString );
			 
			        // Send Message!
			        this.emailSender.send(message);
			}
			
			
			
		}
		else
		{
			//Las contraseñas no coinciden
			return "redirect:/index?NoMatchPasswords";
		}
		
        
		return "redirect:/index?EmailSended";
	}//End
	
	
	@GetMapping({"/","/index"})
	public String index(Model model,HttpServletRequest request ) throws Exception {
		
		model.addAttribute("user", userService.getSession(request));
		model.addAttribute("userForm", new User());
		model.addAttribute("persona", new Persona());
		
		return "/index";
	}//End	
	
	
	@PostMapping("/index")
	public String postUserForm( @ModelAttribute("userForm")User user, BindingResult result, ModelMap model) {
		
		if(result.hasErrors()) {
			model.addAttribute("userForm", user);
			model.addAttribute("formTab","active");
		}else {
			try {//Aca tendras error porque este metodo no existe, pero lo crearemos en la siguiente seccion.
				userService.createUser(user);
				model.addAttribute("userForm", new User());
				model.addAttribute("listTab","active");
				
			} catch (Exception e) {
				model.addAttribute("formErrorMessage",e.getMessage());
				model.addAttribute("userForm", user);
				model.addAttribute("formTab","active");
				model.addAttribute("userList", userService.getAllUsers());
				
			}
			
		}//End_else
		model.addAttribute("userList", userService.getAllUsers());
		System.out.println(model.addAttribute("userList", userService.getAllUsers()));
		
		return "index";
	}//End
	
	public void imprimir(String email) {
		System.out.println(email+"holamundo");
		
	}
	
	
	@GetMapping("/editUser/{id}")
	public String getEditUserForm(Model model, @PathVariable(name ="id")Long id)throws Exception{
		User userToEdit = userService.getUserById(id);

		model.addAttribute("userForm", userToEdit);
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("formTab","active");
		model.addAttribute("editMode","true");
		model.addAttribute("passwordForm",new ChangePasswordForm(id));

		return "user-form/user-view";
	}
	
	@PostMapping("/editUser")
	public String postEditUserForm(@Valid @ModelAttribute("userForm")User user, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("userForm", user);
			model.addAttribute("formTab","active");
			model.addAttribute("editMode","true");
			model.addAttribute("passwordForm",new ChangePasswordForm(user.getId()));
		}else {
			try {
				userService.updateUser(user);
				model.addAttribute("userForm", new User());
				model.addAttribute("listTab","active");
			} catch (Exception e) {
				model.addAttribute("formErrorMessage",e.getMessage());
				model.addAttribute("userForm", user);
				model.addAttribute("formTab","active");
				model.addAttribute("userList", userService.getAllUsers());
				model.addAttribute("editMode","true");
				model.addAttribute("passwordForm",new ChangePasswordForm(user.getId()));
			}
		}
		model.addAttribute("userList", userService.getAllUsers());
		return "user-form/user-view";
	}
	
	@GetMapping("/userForm/cancel")
	public String cancelEditUser(ModelMap model) {
		return "redirect:/login";
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping("/editUser/changePassword")
	public ResponseEntity postEditUseChangePassword(@Valid @RequestBody ChangePasswordForm form, Errors errors) {
		try {
			if( errors.hasErrors()) {
				String result = errors.getAllErrors()
                        .stream().map(x -> x.getDefaultMessage())
                        .collect(Collectors.joining(""));

				throw new Exception(result);
			}
			userService.changePassword(form);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok("Success");
	}
	
	public Integer UsuarioDoctor(HttpServletRequest request,UserService userService) {
		String usuarioDoctor= request.getUserPrincipal().getName();
		///System.out.println("el id es"+IdDoctor2);
		////return IdDoctor2;
		
		////-------------------
		 
		  userService.getIdDoc(usuarioDoctor);
		  Object[] idD=userService.getIdDoc(usuarioDoctor).toArray();
		  Integer idDoct=idD[0].hashCode();
		 //// System.out.println("metodo sin return"+idDoct);
		  return idDoct;
		
	}
		
}
