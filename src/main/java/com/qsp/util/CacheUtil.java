package com.qsp.util;

import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;

import com.qsp.requestdto.WeatherCreationDto;

@Component
public class CacheUtil {
	
	@CachePut(value = "FetchWeatherReportById", key = "#id")
	public WeatherCreationDto updateCacheWhileUpdateWeather(Integer id, WeatherCreationDto dto) {
		return dto;
	}
}
