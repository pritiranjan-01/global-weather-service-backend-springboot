package com.qsp.serviceimplement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.qsp.entity.WeatherReport;
import com.qsp.exception.custom.ServiceUnavailableException;
import com.qsp.requestdto.WeatherDTO;
import com.qsp.responsedto.ResponseStructure;
import com.qsp.service.InternationalWeatherService;
import com.qsp.util.JsonConverter;

@Service
public class InternationalWeatherServiceImplementation implements InternationalWeatherService {

    private final JsonConverter jsonConverter;

	@Value("${global_weather_baseurl}")
	private String base_url;
	
	@Autowired
	private RestTemplate restTemplate;


    InternationalWeatherServiceImplementation(JsonConverter jsonConverter) {
        this.jsonConverter = jsonConverter;
    }
	

	@Override
	public ResponseEntity<ResponseStructure<WeatherDTO>> getInternationalWeatherByCityName(String city) {
		String url = base_url+"/InternationalWeather/"+city; 
		ResponseEntity<ResponseStructure<WeatherDTO>> entity;
		try { 
			entity= restTemplate.exchange(
		
		                    url,
		                    HttpMethod.GET,
		                    null, // RequestBody
		                    new ParameterizedTypeReference<ResponseStructure<WeatherDTO>>() {}
		            );
	    return entity;
		}catch (ResourceAccessException e) {
		   System.out.println("International Weather Service seems to be offline: " + e.getMessage());
		   throw new ServiceUnavailableException("International Weather API is offline");
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<Map<String, WeatherDTO>>> getAllInternationalCityWeather() {
		String url = base_url+"/InternationalWeather";
		ResponseEntity<ResponseStructure<Map<String, WeatherDTO>>> entity;
	    try {
		    entity =	 restTemplate.exchange(
		                    url,
		                    HttpMethod.GET,
		                    null, // RequestBody
		                    new ParameterizedTypeReference<ResponseStructure<Map<String, WeatherDTO>>>() {}
		            );
		return entity;
	   }catch (ResourceAccessException e) {
		   System.out.println("International Weather Service seems to be offline: " + e.getMessage());
		    throw new ServiceUnavailableException("International Weather API is offline");
	   }

	}

	@Override
	public List<WeatherReport> getInternationalWeather(Integer length) {
		ResponseEntity<ResponseStructure<Map<String, WeatherDTO>>> 
							allWeather 	= getAllInternationalCityWeather();
		
		ResponseStructure<Map<String, WeatherDTO>> structure = allWeather.getBody();
		
		Map<String, WeatherDTO> weatherMap = (structure != null) ? structure.getPayload() : new HashMap<>();
		List<Map.Entry<String, WeatherDTO>> entryList = new ArrayList<>(weatherMap.entrySet());
		Collections.shuffle(entryList);
		
		List<WeatherReport> weatherList = new ArrayList<>();
		
		for (Map.Entry<String, WeatherDTO> entry : weatherMap.entrySet()) {
			if (weatherList.size() >= length) break; 
	        
		    String cityName = entry.getKey();          // Key is the City Name
		    WeatherDTO dto = entry.getValue();         // Value is the WeatherDTO object
		    if(dto!=null) {
		    		WeatherReport report = new WeatherReport();
		    		report.setCity(cityName);
		    		report.setTemp(dto.getTemperature());
		    		report.setWeatherType(dto.getType());
		    		weatherList.add(report);
		    }
		}
		return weatherList;
	}
}
