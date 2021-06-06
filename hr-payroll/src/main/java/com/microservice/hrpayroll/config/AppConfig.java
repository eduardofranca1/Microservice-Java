package com.microservice.hrpayroll.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

	// classe para configurar o RestTemplate
	// esse método serve para registrar uma instancia única (singleton) de um objeto do tipo RestTemplate, assim ficando disponível para injetar em outros componentes
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
