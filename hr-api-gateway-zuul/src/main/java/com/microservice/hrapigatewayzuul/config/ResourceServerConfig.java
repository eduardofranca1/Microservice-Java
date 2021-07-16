package com.microservice.hrapigatewayzuul.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Autowired
	private TokenStore tokenStore;
	
	private static final String[] PUBLIC = {"/hr-oauth/oauth/token"}; // definindo quais caminhos serão públicos
	
	private static final String[] OPERATOR = {"/hr-worker/**"}; // definindo as rotas para o user que for operator
	
	private static final String[] ADMIN = {"/hr-payroll/**", "/hr-user/**", "/actuator/**", "/hr-worker/actuator/**", "/hr-oauth/actuator/**"};
	
	
	// método para receber o token
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore);
	}

	// método para configurar as autorizações
	@Override
	public void configure(HttpSecurity http) throws Exception {
		// definindo as autorizações
		
		http.authorizeRequests()
		.antMatchers(PUBLIC).permitAll()
		.antMatchers(HttpMethod.GET, OPERATOR).hasAnyRole("OPERATOR", "ADMIN") // se tiver qualquer um desse roles ele vai conseguir acessar as rotas passados no primeiro parametro
		.antMatchers(ADMIN).hasRole("ADMIN") // só vai conseguir acessar ADMIN se tiver o perfil de "ADMIN"
		.anyRequest().authenticated(); // qualquer rota que não esteja especificada anteriormente terá que está autenticado
		
	}
	
	
}
