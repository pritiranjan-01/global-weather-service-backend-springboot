package com.qsp.configuration;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.resend.Resend;


@Configuration
public class LibraryBeanCreationConfiguration {
	
	@Value("${RESEND_API_KEY}")
	private String RESEND_API_KEY;
	
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
	
	@Bean
    Resend resend() {
        return new Resend(RESEND_API_KEY);
    }

}

