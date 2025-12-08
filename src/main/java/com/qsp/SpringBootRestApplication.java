package com.qsp;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.qsp.serviceimplement.EmailAndPdfServiceImpl;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication // @ComponentScan + @EnableAutoConfiguration + @SpringBootConfiguration
@EnableCaching		  //                      		                    = @Configuration															
@EnableAspectJAutoProxy // for aop
@EnableJpaAuditing // for time stamping
@EnableAsync //for multhithreading
@EnableScheduling
@OpenAPIDefinition
//(
//        info = @Info(
//                title = "Weather Service API",
//                version = "v1",
//                description = "API for retrieving weather data"
//        ),
//        servers = {
//                @Server(
//                        url = "https://weather-service-prod.up.railway.app",
//                        description = "Production Server"
//                )
//        }
//)
public class SpringBootRestApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpringBootRestApplication.class, args);
	}
}
