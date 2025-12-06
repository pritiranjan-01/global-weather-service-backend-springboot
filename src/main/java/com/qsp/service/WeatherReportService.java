package com.qsp.service;

import com.qsp.util.SubscriptionType;

public interface WeatherReportService {
    
    void processUsers(SubscriptionType type, int domesticLimit, int internationalLimit) throws Exception;
    
}