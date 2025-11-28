package com.qsp.runner;

import java.util.Random;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.qsp.entity.Client;
import com.qsp.entity.WeatherReport;
import com.qsp.repository.ClientRepository;
import com.qsp.repository.WeatherRepositoy;
import com.qsp.util.SubscriptionType;
import com.qsp.util.WeatherType;

@Component
public class DataDumpingRunner implements CommandLineRunner{
	
	@Autowired
	private WeatherRepositoy weatherRepositoy;
	@Autowired
	private ClientRepository usersRepository;
	
	@Autowired
	private Random random;

	@Override
	public void run(String... args) throws Exception {

		// Weather Data Dumping
		long count = weatherRepositoy.count();
		count = 150-count;
		if(count>=0) {
		String dummyCity = "DummyCity ";
		for(int i=1;i<=count;i++) {
			String tempcity = dummyCity+i;
			String weathertype = WeatherType.getWeatherType(random.nextInt(8));
			Integer temp = random.nextInt(36)+15;
			WeatherReport weather = new WeatherReport(tempcity,temp,weathertype);
			weatherRepositoy.save(weather);
		   }
		}
	}
}
