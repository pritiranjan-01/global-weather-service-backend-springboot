package com.qsp.serviceimplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.qsp.responsedto.ResponseStructure;
import com.qsp.service.InternationalWeatherService;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;

@Service
public class InternationalWeatherServiceImplementation implements InternationalWeatherService {

	@Value("${baseurl}")
	private String base_url;
	
	@Autowired
	private RestTemplate restTemplate;
	

	@Override
	public <T> ResponseEntity<ResponseStructure<T>> getInternationalWeatherByCityName(String city) {
		String url = base_url+"/InternationalWeather/"+city; 
		ResponseEntity<ResponseStructure<T>> entity =
				 restTemplate.exchange(
		                    url,
		                    HttpMethod.GET,
		                    null, // RequestBody
		                    new ParameterizedTypeReference<ResponseStructure<T>>() {}
		            );
	    return entity;
	}

	@Override
	public <T> ResponseEntity<ResponseStructure<T>> getAllInternationalCityWeather() {
		String url = base_url+"/InternationalWeather";
		ResponseEntity<ResponseStructure<T>> entity =
				 restTemplate.exchange(
		                    url,
		                    HttpMethod.GET,
		                    null, // RequestBody
		                    new ParameterizedTypeReference<ResponseStructure<T>>() {}
		            );
		
	    return entity;
	}

}
