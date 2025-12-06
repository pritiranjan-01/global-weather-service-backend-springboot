package com.qsp.modelmapper;

import org.springframework.stereotype.Component;
import com.qsp.entity.WeatherReport;
import com.qsp.requestdto.WeatherCreationDto;

@Component
public final class RequestWeatherMapper {
	
	public WeatherReport saveWeatherDto_to_weather(WeatherCreationDto dto,WeatherReport report) {
		report.setCity(dto.getCity());
		report.setTemp(dto.getTemp());
		report.setWeatherType(dto.getWeatherType());
		return report;
	}
  
	public WeatherCreationDto weatherreportToToWeatherCreationDto
										(WeatherReport report,WeatherCreationDto dto) {
		dto.setTemp(report.getTemp());
		dto.setWeatherType(report.getWeatherType());
		dto.setCity(report.getCity());
		return dto;
	}
	

}
