package com.qsp.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.qsp.service.WeatherReportService;
import com.qsp.util.SubscriptionType;

@Component
public class WeatherReportSendScheduler {
	
	@Autowired
	private WeatherReportService reportService;
	

	@Scheduled(cron = "0 */2 * * * *") 	 // Once in a Day at 6 AM => Local(5)
	void sendEmailToGOUsers() throws Exception{  	
		reportService.processUsers(SubscriptionType.GO, 5, 0);
	}
    
	
    @Scheduled(cron = "0 0 8 * * *")             // Once in a Day at 6 AM => Local(5) + International(5)
	void sendEmailToPROUsers() throws Exception {	 
		reportService.processUsers(SubscriptionType.PRO, 5, 5);
	}
    
	
    @Scheduled(cron = "0 0 8 * * *")             // Twice in a Day at 6 AM & 6 PM => Local(5) + International(5)
	void sendEmailToMAXUsers() throws Exception{	
		reportService.processUsers(SubscriptionType.MAX, 5, 5);													
	}
}
