package com.qsp.service;

import org.springframework.http.ResponseEntity;

import com.qsp.responsedto.ResponseStructure;

public interface InternationalWeatherService {
	<T>ResponseEntity<ResponseStructure<T>> getInternationalWeatherByCityName(String city);
	
	<T>ResponseEntity<ResponseStructure<T>> getAllInternationalCityWeather();
}
