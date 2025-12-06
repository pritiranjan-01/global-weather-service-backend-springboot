package com.qsp.util;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qsp.entity.EmailLogReport;
import com.qsp.entity.WeatherReport;
import com.qsp.responsedto.WeatherEmailDTO;

@Component
public class JsonConverter {

	@Autowired
	private ObjectMapper mapper;
	
	public String convertToJsonString(List<WeatherReport> logReport) {
		try {
			return mapper.writeValueAsString(logReport);
		} catch (JsonProcessingException e) {
			 throw new RuntimeException("Failed to convert list to JSON", e);
		}
	}
	
	public List<WeatherReport> convertJsonToList(String json) {
	    try {
	        return mapper.readValue(json, new TypeReference<List<WeatherReport>>() {});
	    } catch (IOException e) {
	        throw new RuntimeException("Failed to convert JSON to List<EmailLogReport>", e);
	    }
	}
}
