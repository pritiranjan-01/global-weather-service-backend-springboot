package com.qsp.configuration;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class LibraryBeanCreationConfiguration {
	
	@Bean
	Random createRandomObject() {
		return new Random();
	}
	
	@Bean("resttemplete")
	RestTemplate createRestTemplete() {
		return new RestTemplate();
	}
	
	@Bean("otpholder")
	Map<String, Object[]> createdOTPHolder(){
		return new LinkedHashMap<String, Object[]>(); 
	}

}

