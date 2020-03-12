package com.bemedica.springboot.app.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bemedica.springboot.app.dto.ChangePasswordForm;
import com.bemedica.springboot.app.exception.CustomeFieldValidationException;
import com.bemedica.springboot.app.models.entity.Paciente;
import com.bemedica.springboot.app.models.entity.Persona;
import com.bemedica.springboot.app.models.entity.User;
import com.bemedica.springboot.app.repository.PacienteRepository;
import com.bemedica.springboot.app.repository.PersonaRepository;
import com.bemedica.springboot.app.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder; //agregue private para resolver el problema del 19-02-20
	
	@Autowired
	UserRepository repository;
	
	@Autowired
	PersonaRepository repositoryPersona;
	
	@Autowired
	PacienteRepository repositoryPaciente;
	
	@PersistenceContext
	private EntityManager em;
	
	private boolean checkEmailAvailable(User user) throws Exception 
	{
		Optional<User> userFound = repository.findByEmail(user.getEmail());
		
		if (userFound.isPresent()) 
		{
			return false;
		}
		
		return true;
	}

	
	private boolean checkUserNickAvailable(User user) throws Exception
	{
		Optional<User> userFound = repository.findByUsername(user.getUsername());
		
		if(userFound.isPresent()) 
		{
			return false;
		}
		
		return true;
	}

	
	@Override
	public User createUser(User user) throws Exception 
	{
		User userDummy = new User();
		
		//Checamos si ya existe el usuario
		if(checkUserNickAvailable(user) && checkEmailAvailable(user))
		{
			//Se guarda primero la persona
			Persona per = new Persona();
			per.setPersonaNombre(user.getFirstName());
			per.setPersonaAP(user.getLastName());
			per.setPersonaAM(user.getApmat());
			per.setPersonaEmail(user.getEmail());
			per = repositoryPersona.save(per);
			
			//Ahora se guarda el paciente
			Paciente pac = new Paciente();
			pac.setExpediente("pac-2-" + (10000 + per.getId()));
			pac.setEstadoCivil(" ");
			pac.setPersona(per);
			pac = repositoryPaciente.save(pac);
			
			//Y por ultimo se guarda en el user_pac
			user.setPacienteId((pac.getId()).toString());
			user.setStatus(0);
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			user = repository.save(user);
		}
		else
		{
			if(checkUserNickAvailable(user))
			{
				//No esta disponible el correo
				userDummy.setFirstName("EmailBusy");
				return userDummy;
			}
			else
			{
				if(checkEmailAvailable(user))
				{
					//No esta disponible el username
					userDummy.setFirstName("NickBusy");
					return userDummy;
				}
				else
				{
					//No esta disponible ninguno
					userDummy.setFirstName("EmailNickBusy");
					return userDummy;
				}
			}
		}
		
		return user;
	}

	@Override
	public User getUserById(Long id) throws Exception {
		return repository.findById(id).orElseThrow(() -> new Exception("El usuario no existe."));
	}

	@Override
	public User updateUser(User fromUser) throws Exception {
		User toUser = getUserById(fromUser.getId());
		mapUser(fromUser, toUser);
		return repository.save(toUser);
	}

	@Override
	public User changePassword(ChangePasswordForm form) throws Exception {
		User user = getUserById(form.getId());

		if ( !user.getPassword().equals(form.getCurrentPassword())) {
			throw new Exception ("Current Password invalido.");
		}
		
		if( user.getPassword().equals(form.getNewPassword())) {
			throw new Exception ("Nuevo debe ser diferente al password actual.");
		}
		
		if( !form.getNewPassword().equals(form.getConfirmPassword())) {
			throw new Exception ("Nuevo Password y Current Password no coinciden.");
		}
		String encodePassword = bCryptPasswordEncoder.encode(form.getNewPassword());
		user.setPassword(encodePassword);
		return repository.save(user);
	}
	
	public User getSession(HttpServletRequest request) throws Exception  //metodo que se hizo para poner el nombre del usurio logueado y ponerlo en cada template el (model) 
	{
		com.bemedica.springboot.app.models.entity.User u  = new User();
		//rescata el nombre de la sesion
		try 
		{
			if(request.getUserPrincipal().getName().toString() != null)
			{
				System.out.println("Hay Sesion");
				u = repository.findByUsername(request.getUserPrincipal().getName().toString()).orElseThrow(() -> new UsernameNotFoundException("Login Username Invalido."));
			}
			
		}
		catch(Exception e) 
		{
			System.out.println("No hay Sesion");
			u.setUsername("No_Session");
			u.setFirstName("No_Session");
		}
		//cierra
		return u;
	}
	
	
	protected void mapUser(User from,User to) {
		to.setEmail(from.getEmail());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setUsername(from.getUsername());
		///to.setRoles(from.getRoles());
		to.setId(from.getId());
	}


	@Override
	public Iterable<User> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object> getIdDoc(String usuarioDoctor) {
		return em.createNativeQuery("select medico_id from user_med where user_medname='"+usuarioDoctor+"' or email='"+usuarioDoctor+"'").getResultList();
	}
}
