package com.bemedica.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//Indica que esta clase es de configuracion y necesita ser cargada durante el inicio del server
@Configuration

//Indica que esta clase sobreescribira la implmentacion de seguridad web
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
	String[] resources = new String[]{
			"/css/**", "/img/*","/vendor/**","/js/*", "/plugins/**", "/uploads/**"
            };
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http
    	
        .authorizeRequests()
        .antMatchers(resources).permitAll()  
        .antMatchers("/", "/fotos","/index", "/buscar_doctor_info","/login", "/intervalo_tiempo", "/findIntervalo", "/Reenviar_Correo", "/signup", "/allevents", "/event", "/buscar_doctor", "/citas", "/findCitas", "/confirmar-correo", "/EnvioMail", "/getMedico").permitAll()
            .anyRequest().authenticated()
            .and()
        .formLogin()
            .loginPage("/login")
            .permitAll()
            .defaultSuccessUrl("/index")
            .failureUrl("/index?NoActive")
            .usernameParameter("username")
            .passwordParameter("password")
            .and()
            .csrf().disable()
        .logout()
            .permitAll()
            .logoutSuccessUrl("/index");
    }

	BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
		bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
        return bCryptPasswordEncoder;
    }

    @Autowired
    UserDetailsService userDetails;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { 
    	//Especificar el encargado del login y encriptacion del password
        auth.userDetailsService(userDetails).passwordEncoder(passwordEncoder());
    }
}
