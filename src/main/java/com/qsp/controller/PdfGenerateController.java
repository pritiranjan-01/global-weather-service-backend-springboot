package com.qsp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qsp.service.EmailAndPdfService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/weather-report")
public class PdfGenerateController{
	
	private final EmailAndPdfService emailPdfService;
	
	@GetMapping("/download")
	public void downloadPdf(@RequestParam Integer id, HttpServletResponse response) throws Exception {
	    response.setContentType("application/pdf");
	    String headerKey = "Content-Disposition";
	    String headerValue = "attachment; filename=Weather_Report_" + id + ".pdf";
	    response.setHeader("Content-Disposition", "inline; filename=...");
//	    response.setHeader(headerKey, headerValue);
	    emailPdfService.generatePdfWeatherReport(id, response.getOutputStream());
	}
}
