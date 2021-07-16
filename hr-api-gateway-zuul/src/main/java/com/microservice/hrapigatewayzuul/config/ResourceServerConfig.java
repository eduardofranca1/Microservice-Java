package com.microservice.hrapigatewayzuul.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

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
		
		http.cors().configurationSource(corsConfigurationSource());
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowedOrigins(Arrays.asList("*")); // quem vai poder acessar o sistema
		corsConfig.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "PATCH")); // quais metodos HTTP vai ser permitido
		corsConfig.setAllowCredentials(true); // permitindo credenciais
		corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // quais cabeçalhos vai ser permitido
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);
		return source;
	}
	
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}
	
}
