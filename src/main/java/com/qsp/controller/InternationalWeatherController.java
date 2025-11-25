package com.qsp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qsp.responsedto.ResponseStructure;
import com.qsp.service.InternationalWeatherService;

@RestController
@RequestMapping("/global")
public class InternationalWeatherController {

	@Autowired
	private InternationalWeatherService weatherService;

	@GetMapping("/{city}")
	public <T> ResponseEntity<ResponseStructure<T>> getInternatinalWeatherByCityName(
			@PathVariable("city") String city) {
		return weatherService.getInternationalWeatherByCityName(city);
	}

	@GetMapping
	public <T> ResponseEntity<ResponseStructure<T>> getAllInternatinalWeather() {
		return weatherService.getAllInternationalCityWeather();
	}
}
