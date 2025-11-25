package com.qsp.service;

import java.util.List
;
import java.util.Map;

import com.qsp.entity.WeatherReport;
import com.qsp.requestdto.WeatherCreationDto;
import com.qsp.requestdto.WeatherReportUpdateRequestDTO;

public interface WeatherService {
	String saveCityWeatherInfoService(WeatherCreationDto weatherCreationDto);
	
	WeatherCreationDto getWeatherReportByIdService(Integer id);
	
	List<WeatherReport> getAllWeatherReports();
	
	Map<String,WeatherCreationDto> doUpdateWeatherReport(Integer id, WeatherReportUpdateRequestDTO reqdto);
	
	String deleteWeatherServiceById(Integer id);
	
	List<WeatherReport> getWeatherPageService(Integer pageNumber, Integer pageSize);
	
}
