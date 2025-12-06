package com.qsp.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.qsp.entity.WeatherReport;
import com.qsp.requestdto.WeatherDTO;
import com.qsp.responsedto.ResponseStructure;

public interface InternationalWeatherService {
	ResponseEntity<ResponseStructure<WeatherDTO>> getInternationalWeatherByCityName(String city);

	ResponseEntity<ResponseStructure<Map<String, WeatherDTO>>> getAllInternationalCityWeather();
	
	List<WeatherReport> getInternationalWeather(Integer length);
}
