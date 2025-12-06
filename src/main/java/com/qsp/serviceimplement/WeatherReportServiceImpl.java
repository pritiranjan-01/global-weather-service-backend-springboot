package com.qsp.serviceimplement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qsp.entity.Client;
import com.qsp.repository.ClientRepository;
import com.qsp.service.EmailAndPdfService;
import com.qsp.service.WeatherReportService;
import com.qsp.util.SubscriptionType;

@Service
public class WeatherReportServiceImpl  implements WeatherReportService {

	@Autowired
	private ClientRepository clientRepo;

	@Autowired
	private EmailAndPdfService emailService;
	
	public void processUsers(SubscriptionType type, int domesticLimit, int internationalLimit) throws Exception {

	    List<Client> clients = clientRepo.findBySubscriptionTypeAndIsActiveTrue(type);

	    if (clients.isEmpty()) {
	        return; // no users → do nothing (saves API calls)
	    }

	    for (Client client : clients) {
	            emailService.sendWeatherReportInEmail(client.getEmail(), domesticLimit, internationalLimit);
	      }
	}
}