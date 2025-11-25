package com.qsp;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication // @ComponentScan + @EnableAutoConfiguration + @SpringBootConfiguration
@EnableCaching		  //                      		                    = @Configuration															
@EnableAspectJAutoProxy // for aop
@EnableJpaAuditing // for time stamping
@EnableAsync //for multhithreading
@OpenAPIDefinition(info = @Info(title = "REST_API",
								description = "Demo learning project",
								version = "1.0"),
					servers = { @Server(url = "http://localhost:8080",
								      description = "local dev server")})
public class SpringBootRestApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestApplication.class, args);
	}
}
