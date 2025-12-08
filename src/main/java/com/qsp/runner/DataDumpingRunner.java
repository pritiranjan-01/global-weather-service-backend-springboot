package com.qsp.runner;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.qsp.entity.WeatherReport;
import com.qsp.repository.ClientRepository;
import com.qsp.repository.WeatherRepositoy;
import com.qsp.util.WeatherType;

@Component
public class DataDumpingRunner implements CommandLineRunner {

	@Autowired
	private WeatherRepositoy weatherRepositoy;
	@Autowired
	private Random random;

	@Override
	public void run(String... args) throws Exception {

		long count = weatherRepositoy.count();count=150-count;

		for(int i = 1;i<=count;i++)
		{

			String city = cities.get(random.nextInt(cities.size()));

			// pick a realistic weather type
			String weatherType = getWeatherType(random);

			// generate correct temperature based on weather type
			int temperature = getTemperature(weatherType, random);

			WeatherReport weather = new WeatherReport(city, temperature, weatherType);
			weatherRepositoy.save(weather);
			
		}
	}

	List<String> cities = Arrays.asList("Mumbai", "Delhi", "Bengaluru", "Hyderabad", "Ahmedabad", "Chennai", "Kolkata",
			"Pune", "Jaipur", "Surat", "Lucknow", "Kanpur", "Nagpur", "Indore", "Thane", "Bhopal", "Visakhapatnam",
			"Patna", "Vadodara", "Ghaziabad", "Ludhiana", "Agra", "Nashik", "Faridabad", "Meerut", "Rajkot",
			"Kalyan-Dombivli", "Vasai-Virar", "Varanasi", "Srinagar", "Aurangabad", "Dhanbad", "Amritsar",
			"Navi Mumbai", "Prayagraj", "Howrah", "Ranchi", "Gwalior", "Jabalpur", "Coimbatore", "Vijayawada",
			"Jodhpur", "Madurai", "Raipur", "Kota", "Guwahati", "Chandigarh", "Solapur", "Hubli-Dharwad", "Bareilly",
			"Moradabad", "Mysore", "Gurugram", "Aligarh", "Jalandhar", "Tiruchirappalli", "Bhubaneswar", "Salem",
			"Warangal", "Mira-Bhayandar", "Thiruvananthapuram", "Bhiwandi", "Saharanpur", "Gorakhpur", "Guntur",
			"Bikaner", "Amravati", "Noida", "Jamshedpur", "Bhilai", "Cuttack", "Firozabad", "Kochi", "Nellore",
			"Bhavnagar", "Dehradun", "Durgapur", "Asansol", "Rourkela", "Nanded", "Kolhapur", "Ajmer", "Akola",
			"Kalaburagi", "Jamnagar", "Ujjain", "Loni", "Siliguri", "Jhansi", "Ulhasnagar", "Jammu", "Sangli",
			"Mangalore", "Erode", "Belgaum", "Kurnool", "Malegaon", "Tumkur");

	String getWeatherType(Random random) {
		WeatherType[] types = WeatherType.values();
		return types[random.nextInt(types.length)].name();
	}

	int getTemperature(String weatherType, Random random) {

		switch (weatherType) {

		case "COLD":
			return random.nextInt(5, 19); // 5–18°C

		case "MEDIUM":
			return random.nextInt(18, 29); // 18–28°C

		case "HOT":
			return random.nextInt(30, 46); // 30–45°C

		case "SUNNY":
			return random.nextInt(22, 35); // 22–34°C

		case "SMOG":
			return random.nextInt(8, 21); // 8–20°C

		case "SNOWY":
			return random.nextInt(-5, 6); // -5–5°C

		case "RAINY":
			return random.nextInt(20, 31); // 20–30°C

		case "STORM":
			return random.nextInt(22, 33); // 22–32°C

		case "WINDY":
			return random.nextInt(18, 29); // 18–28°C
		}

		return 25; // default fallback
	}

}
