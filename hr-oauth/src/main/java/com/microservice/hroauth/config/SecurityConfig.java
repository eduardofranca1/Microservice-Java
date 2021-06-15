package com.microservice.hroauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{ // WebSecurityConfigurerAdapter para implementar um endpoint automatico de autenticação usando o framework nesta classe

	// configurando a autenticação, quando a aplicação receber as credenciais do usuário e vai chamar o usuário pelo username e comparar a senha para autenticar
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService) // para configurar quem vai ser o user details service dele
		.passwordEncoder(passwordEncoder); // e aqui configurar quem vai fazer o hash da senha para comparar		
	}

	@Override
	@Bean // o override foi chamado só para colocar o bean porque está sendo injetado isso aqui em outro componente e para o outro componente ter acesso foi necessário fazer isso
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}	
	
}
