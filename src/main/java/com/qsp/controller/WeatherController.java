package com.qsp.controller;

import java.util.List;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.qsp.entity.WeatherReport;
import com.qsp.modelmapper.ResponseEntityMapper;
import com.qsp.modelmapper.ResponseStructureModelMapper;
import com.qsp.requestdto.WeatherCreationDto;
import com.qsp.requestdto.WeatherReportUpdateRequestDTO;
import com.qsp.responsedto.ResponseStructure;
import com.qsp.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/weather")
@Tag(name = "/weather", description = "Save, Update, Remove, Delete Weather")
public class WeatherController {

	private final WeatherService weatherService;
	
	private final ResponseStructureModelMapper responseStructure;
	
	private final ResponseEntityMapper responseEntityMapper;
	
	@PostMapping
	@Operation(description = "Post operation to save weather info as per the city", summary = "Post operation to save weather info as per the city")
	public ResponseEntity<ResponseStructure<String>> saveWeatherReport(
			@RequestBody WeatherCreationDto dto) {
		String serviceResponse = weatherService.saveCityWeatherInfoService(dto);
		ResponseStructure<String> structure = responseStructure
				.mapToResponseStructure(HttpStatus.CREATED, "String",serviceResponse);
		return responseEntityMapper.getResponseEntity(structure, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get the temperature and weather type as per id as path variable")
	public ResponseEntity<ResponseStructure<WeatherCreationDto>> getWeatherById(
			@PathVariable("id") Integer id) {
		WeatherCreationDto payload = weatherService.getWeatherReportByIdService(id);
		ResponseStructure<WeatherCreationDto> structure = responseStructure
				.mapToResponseStructure(HttpStatus.OK, "String",payload);
		return responseEntityMapper.getResponseEntity(structure, HttpStatus.OK);
	}

	@GetMapping
	@Operation(summary = "Get All the Weather Data ")
	public ResponseEntity<ResponseStructure<List<WeatherReport>>> getAllWeatherReport() {
		List<WeatherReport> payload = weatherService.getAllWeatherReports();
		ResponseStructure<List<WeatherReport>> structure = responseStructure
				.mapToResponseStructure(HttpStatus.OK, "String",payload);
		return responseEntityMapper.getResponseEntity(structure, HttpStatus.OK);
		
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update the Weather Data")
	public ResponseEntity<ResponseStructure<Map<String, WeatherCreationDto>>> updateWeatherReport(
			@PathVariable Integer id,
			@Valid @RequestBody WeatherReportUpdateRequestDTO reqDto) {
		Map<String, WeatherCreationDto> serviceResponse = weatherService
				.doUpdateWeatherReport(id, reqDto);
		ResponseStructure<Map<String, WeatherCreationDto>> structure = 
				responseStructure.mapToResponseStructure(HttpStatus.OK, "Object", serviceResponse);
		return responseEntityMapper.getResponseEntity(structure, HttpStatus.OK);

	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete Weather Report by Id")
	public ResponseEntity<ResponseStructure<String>> deleteWeatherDataById(@PathVariable Integer id) {
		String payload = weatherService.deleteWeatherServiceById(id);
		ResponseStructure<String> structure = responseStructure.
				mapToResponseStructure(HttpStatus.OK, "Object", payload);
		return responseEntityMapper.getResponseEntity(structure, HttpStatus.OK);

	}

	@GetMapping("/page")
	@Operation(summary = "Get Weather Report by Page(Default page is 50)")
	public ResponseEntity<ResponseStructure<List<WeatherReport>>> getWeatherPage(
			@RequestParam(name = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
			@RequestParam(name = "pageSize", required = false, defaultValue = "25") Integer pageSize) {
		List<WeatherReport> payload = weatherService.getWeatherPageService(pageNumber, pageSize);
		ResponseStructure<List<WeatherReport>> structure = responseStructure.
				mapToResponseStructure(HttpStatus.OK, "Object", payload);
		return responseEntityMapper.getResponseEntity(structure, HttpStatus.OK);
	}
}
