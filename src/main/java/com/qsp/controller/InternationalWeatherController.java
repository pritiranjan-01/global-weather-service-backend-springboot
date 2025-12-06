package com.qsp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qsp.requestdto.WeatherDTO;
import com.qsp.responsedto.ResponseStructure;
import com.qsp.service.InternationalWeatherService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/global")
@Tag(name = "/global", description = "External Service API to fetch International Weather Report ")
public class InternationalWeatherController {

	@Autowired
	private InternationalWeatherService weatherService;

	@Operation(summary = "Get global weather report by City name")
	@GetMapping("/{city}")
	public <T> ResponseEntity<ResponseStructure<WeatherDTO>> getInternatinalWeatherByCityName(
			@PathVariable("city") String city) {
		return weatherService.getInternationalWeatherByCityName(city);
	}

	@Operation(summary = "Get all the global weather report")
	@GetMapping
	public ResponseEntity<ResponseStructure<Map<String, WeatherDTO>>> getAllInternatinalWeather() {
		return weatherService.getAllInternationalCityWeather();
	}
}
