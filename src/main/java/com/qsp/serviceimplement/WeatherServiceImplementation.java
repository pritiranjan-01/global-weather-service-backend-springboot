package com.qsp.serviceimplement;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qsp.entity.WeatherReport;
import com.qsp.modelmapper.RequestWeatherMapper;
import com.qsp.repository.WeatherRepositoy;
import com.qsp.requestdto.WeatherCreationDto;
import com.qsp.requestdto.WeatherReportUpdateRequestDTO;
import com.qsp.service.WeatherService;
import com.qsp.util.CacheUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WeatherServiceImplementation implements WeatherService{

//	@Autowired
	private final WeatherRepositoy weatherRepo;
	
	private final RequestWeatherMapper weatherMapper;
	
	private final CacheUtil cacheUtil;
	
	@Override
	@Transactional
	public String saveCityWeatherInfoService(WeatherCreationDto dto) {
		WeatherReport report = weatherMapper.saveWeatherDto_to_weather(dto, new WeatherReport());
		report = weatherRepo.save(report);
		return dto.toString()+" Weather report saved, with id: "+report.getId();
	}

	@Override
	@Cacheable(value = "FetchWeatherReportById", key = "#id")
	public  WeatherCreationDto getWeatherReportByIdService(Integer id) {
		Optional<WeatherReport> weatherReport = weatherRepo.findById(id);
		if(weatherReport.isEmpty()) 
			throw new NoSuchElementException("No weather report found for id:"+id);
		WeatherCreationDto saveWeatherDTO = weatherMapper.weatherreportToToWeatherCreationDto(weatherReport.get(), new WeatherCreationDto());
		return saveWeatherDTO;
	}

	@Override
	public List<WeatherReport> getAllWeatherReports() {
		return weatherRepo.findAll();
	}

	@Override
	@Transactional
	public Map<String, WeatherCreationDto> doUpdateWeatherReport(Integer id, WeatherReportUpdateRequestDTO reqdto) {
		Optional<WeatherReport> report = weatherRepo.findById(id);
		if(report.isEmpty())
			throw new NoSuchElementException("No weather report found for id:"+id);
		WeatherReport reportObject = report.get();

		WeatherCreationDto olddata=weatherMapper.weatherreportToToWeatherCreationDto(reportObject, new WeatherCreationDto());
		WeatherCreationDto newdata=new WeatherCreationDto(reportObject.getCity(),reqdto.getTemp(),reqdto.getDescription());
		
		reportObject.setTemp(reqdto.getTemp());
		reportObject.setWeatherType(reqdto.getDescription());
		reportObject.setTemp(reqdto.getTemp());
		reportObject.setWeatherType(reqdto.getDescription());
		
		weatherRepo.save(reportObject);
		cacheUtil.updateCacheWhileUpdateWeather(id, newdata);
		return Map.of( 	"oldData",olddata,
				        "newData",newdata
				     );
	}

	@Override
	@Transactional
	@CacheEvict(value = "FetchWeatherReportById", key = "#id")
	public String deleteWeatherServiceById(Integer id) {
		if(!weatherRepo.existsById(id)) 
			throw new NoSuchElementException("No weather report found for id:"+id);			
		weatherRepo.deleteById(id);
		return "Weather data deleted successfully ";
	}

	@Override
	public List<WeatherReport> getWeatherPageService(Integer pageNumber, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		return weatherRepo.findAll(pageable).getContent();
	}
}
