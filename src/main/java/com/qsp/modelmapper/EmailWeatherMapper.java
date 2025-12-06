package com.qsp.modelmapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.qsp.entity.WeatherReport;
import com.qsp.responsedto.WeatherEmailDTO;

@Component
public class EmailWeatherMapper {
	
	public List<WeatherEmailDTO> maptoEmailWeather(List<WeatherReport> report) {
	    return report.stream()
	          .map(this::maptoEmailWeather) // Use method reference
	          .collect(Collectors.toList()); 
	}

	private WeatherEmailDTO maptoEmailWeather(WeatherReport report) {
	    return new WeatherEmailDTO(report.getCity(), report.getWeatherType(), report.getTemp());
	}
}
