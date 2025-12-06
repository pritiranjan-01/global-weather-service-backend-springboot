package com.qsp.service;

import java.io.OutputStream;

public interface EmailAndPdfService {
	
	void sendWeatherReportInEmail(String email, int domesticLimit, int internationalLimit);
	
	void generatePdfWeatherReport(Integer pdfId, OutputStream outputStream) throws Exception;
	
}
