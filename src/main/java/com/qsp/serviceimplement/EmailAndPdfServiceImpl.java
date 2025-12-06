package com.qsp.serviceimplement;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.qsp.entity.EmailLogReport;
import com.qsp.entity.WeatherReport;
import com.qsp.exception.custom.ServiceUnavailableException;
import com.qsp.modelmapper.EmailWeatherMapper;
import com.qsp.repository.ClientRepository;
import com.qsp.responsedto.WeatherEmailDTO;
import com.qsp.service.EmailAndPdfService;
import com.qsp.service.EmailLogService;
import com.qsp.service.InternationalWeatherService;
import com.qsp.service.WeatherService;
import com.qsp.util.JsonConverter;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailAndPdfServiceImpl implements EmailAndPdfService {

	private final WeatherService weatherService;
	private final InternationalWeatherService intlWeatherService;
	private final EmailLogService emaillLogService;
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final EmailWeatherMapper emailWeatherMapper; // map from entity to EmailWeatherDTO and adding advice 
    private final JsonConverter jsonConverter;
	
    @Async
	@Override
	public void sendWeatherReportInEmail(String clientEmail, int localLimit, int internationalLimit) {

	    List<WeatherReport> finalReportList = new ArrayList<>();
	    
	    if (localLimit > 0) {
	        List<WeatherReport> localWeather = weatherService.getRandomWeather(localLimit);
	        finalReportList.addAll(localWeather);
	    }

	    // Only fetch International if the limit is > 0 (Skipped for GO users)
	    try {
	        List<WeatherReport> intlWeather = intlWeatherService.getInternationalWeather(internationalLimit);
	        finalReportList.addAll(intlWeather);
	    } catch (ServiceUnavailableException e) {
	        System.out.println("Intl API offline, skipping international data...");
	    }

		// Save the log to generate id which will be used to download PDF using the ID.
        EmailLogReport logReport = new EmailLogReport();
        logReport.setClientEmail(clientEmail);
        logReport.setJsonData(jsonConverter.convertToJsonString(finalReportList));
        EmailLogReport savedEmailLog = emaillLogService.saveEmailLog(logReport);
		
        // Converted to Email DTO as LogReport contains Raw data and by doing this Advice will be added 
		List<WeatherEmailDTO> listWeather = emailWeatherMapper.maptoEmailWeather(finalReportList);
		
		
        // 1. THYMELEAF CONTEXT
        Context context = new Context();
        context.setVariable("cityList", listWeather);
        // Important: Pass the download link (assume Report ID is 101)
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        context.setVariable("downloadLink", baseUrl+"/weather-report/download?id="+savedEmailLog.getId());

        // 2. GENERATE HTML
        String htmlContent = templateEngine.process("weather-report", context);

        // 3. SEND EMAIL
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(clientEmail);
        helper.setSubject("Daily Weather Update: " + listWeather.get(0).getCityName() + " and more");
        helper.setFrom("pritiranjan.mohanty2003@gmail.com", "Weather Daily Updates"); // "Envelope" Name
        helper.setText(htmlContent, true); // Set to TRUE for HTML
        mailSender.send(message);
        System.out.println("Email sent successfully to " + clientEmail);
		}catch (MessagingException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
      
	}
	
	@Override
	public void generatePdfWeatherReport(Integer pdfId, OutputStream outputStream) throws IOException {
		EmailLogReport emailLogReport = emaillLogService.getEmailLogReport(pdfId);
		String json = emailLogReport.getJsonData();
		List<WeatherReport> weatherReport = jsonConverter.convertJsonToList(json);
		List<WeatherEmailDTO> WeatherEmailDto = emailWeatherMapper.maptoEmailWeather(weatherReport);
		generatePdf(WeatherEmailDto, outputStream);
		
	}
	
	private void generatePdf(List<WeatherEmailDTO> cityList, OutputStream outputStream) throws IOException {
	    Document document = new Document(PageSize.A4, 30, 30, 30, 30);
	    PdfWriter.getInstance(document, outputStream);
	    document.open();

	    // --- FONTS ---
	    Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, new Color(30, 96, 210));
	    Font dateFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.GRAY);
	    Font cityFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Color.BLACK);
	    Font adviceFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 9, new Color(80, 80, 80));

	    // --- 1. HEADER ---
	    Paragraph title = new Paragraph("Daily Weather Report", titleFont);
	    title.setAlignment(Element.ALIGN_CENTER);
	    document.add(title);

	    Paragraph subtitle = new Paragraph("Forecast generated on " + LocalDate.now(), dateFont);
	    subtitle.setAlignment(Element.ALIGN_CENTER);
	    subtitle.setSpacingAfter(25);
	    document.add(subtitle);

	    // --- 2. MAIN GRID (With Spacing) ---
	    // We use 3 columns: [Card 1] [Spacer] [Card 2]
	    PdfPTable mainTable = new PdfPTable(3);
	    mainTable.setWidthPercentage(100);
	    mainTable.setWidths(new float[]{48f, 4f, 48f}); // 4% gap in the middle

	    for (int i = 0; i < cityList.size(); i++) {
	        WeatherEmailDTO city = cityList.get(i);

	        // --- Create The Card (Nested Table) ---
	        PdfPTable card = new PdfPTable(new float[]{2.5f, 1f});
	        card.setWidthPercentage(100);

	        // LEFT Content
	        PdfPCell leftContent = new PdfPCell();
	        leftContent.setBorder(Rectangle.NO_BORDER);
	        leftContent.addElement(new Paragraph(city.getCityName(), cityFont));
	        
	        // Add Weather "Badge" (Colored Box instead of emoji)
	        PdfPTable badge = createWeatherBadge(city.getWeatherType());
	        leftContent.addElement(badge);
	        
	        // Advice Text
	        Paragraph pAdvice = new Paragraph(city.getAdvice(), adviceFont);
	        pAdvice.setSpacingBefore(5);
	        leftContent.addElement(pAdvice);
	        
	        card.addCell(leftContent);

	        // RIGHT Content (Temp)
	        Font tempFont = getTempFont(city.getTemperature());
	        PdfPCell rightContent = new PdfPCell(new Phrase(city.getTemperature() + "°C", tempFont));
	        rightContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        rightContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        rightContent.setBorder(Rectangle.NO_BORDER);
	        card.addCell(rightContent);

	        // --- WRAPPER CELL ---
	        // This cell holds the card and gives it the Border
	        PdfPCell wrapperCell = new PdfPCell(card);
	        wrapperCell.setBorder(Rectangle.BOX);
	        wrapperCell.setBorderColor(new Color(220, 220, 220)); // Light Gray Border
	        wrapperCell.setBorderWidth(1f);
	        wrapperCell.setPadding(12);
	        wrapperCell.setBackgroundColor(new Color(252, 252, 252)); // Very faint gray bg
	        
	        // Add the card to the grid
	        mainTable.addCell(wrapperCell);

	        // --- HANDLING THE GRID LAYOUT ---
	        
	        // If this is the first item in a row (Left side), add a spacer cell next
	        if (i % 2 == 0) {
	            PdfPCell spacer = new PdfPCell();
	            spacer.setBorder(Rectangle.NO_BORDER);
	            mainTable.addCell(spacer);
	        } else {
	            // If this is the second item (Right side), we just finished a row.
	            // We need to add an empty row for VERTICAL spacing
	            PdfPCell emptyRow = new PdfPCell();
	            emptyRow.setColspan(3);
	            emptyRow.setFixedHeight(15f); // 15pt vertical gap
	            emptyRow.setBorder(Rectangle.NO_BORDER);
	            mainTable.addCell(emptyRow);
	        }
	    }

	    // --- HANDLE ODD NUMBER OF ITEMS ---
	    // If we have an odd number (like 5), the loop finished on the Left side.
	    // We added a Spacer, but we need to fill the Right slot with an invisible cell 
	    // to complete the table row properly.
	    if (cityList.size() % 2 != 0) {
	        PdfPCell emptySlot = new PdfPCell();
	        emptySlot.setBorder(Rectangle.NO_BORDER);
	        mainTable.addCell(emptySlot);
	    }

	    document.add(mainTable);
	    
	    // Footer
	    Paragraph footer = new Paragraph("© 2025 Global Weather Service", dateFont);
	    footer.setAlignment(Element.ALIGN_CENTER);
	    footer.setSpacingBefore(20);
	    document.add(footer);

	    document.close();
	}

	// --- NEW HELPERS ---

	// 1. Dynamic Badge Creator (Replaces missing Emojis)
	private PdfPTable createWeatherBadge(String type) {
	    if (type == null) type = "Unknown";
	    type = type.toUpperCase();

	    // Default Gray (for unknown types)
	    Color bgColor = new Color(240, 240, 240);
	    Color txtColor = Color.BLACK;

	    // --- 1. HOT / SUNNY ---
	    if (type.contains("SUN") || type.contains("HOT") || type.contains("WARM")) { 
	        bgColor = new Color(255, 248, 220); // Light Yellow
	        txtColor = new Color(139, 69, 19);  // Brown
	    } 
	    // --- 2. RAIN ---
	    else if (type.contains("RAIN") || type.contains("DRIZZLE")) { 
	        bgColor = new Color(224, 255, 255); // Light Cyan
	        txtColor = new Color(0, 100, 0);    // Dark Green
	    } 
	    // --- 3. STORM ---
	    else if (type.contains("STORM") || type.contains("THUNDER")) { 
	        bgColor = new Color(230, 230, 250); // Lavender
	        txtColor = new Color(75, 0, 130);   // Indigo
	    }
	    // --- 4. COLD / SNOW (New) ---
	    else if (type.contains("COLD") || type.contains("SNOW") || type.contains("FREEZE")) {
	        bgColor = new Color(240, 248, 255); // Alice Blue
	        txtColor = new Color(25, 25, 112);  // Midnight Blue
	    }
	    // --- 5. SMOG / FOG (New - Fixes the Gray Smog) ---
	    else if (type.contains("SMOG") || type.contains("FOG") || type.contains("MIST") || type.contains("HAZE")) {
	        bgColor = new Color(229, 229, 229); // Darker Gray Background
	        txtColor = new Color(80, 80, 80);   // Dark Gray Text
	    }
	    // --- 6. MEDIUM / PLEASANT (New - Fixes the Gray Medium) ---
	    else if (type.contains("MEDIUM") || type.contains("CLEAR") || type.contains("PLEASANT")) {
	        bgColor = new Color(240, 255, 240); // Honeydew (Pale Green)
	        txtColor = new Color(34, 139, 34);  // Forest Green
	    }

	    Font badgeFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, txtColor);
	    
	    // Create the badge table
	    PdfPTable badgeTable = new PdfPTable(1);
	    badgeTable.setWidthPercentage(40); 
	    badgeTable.setHorizontalAlignment(Element.ALIGN_LEFT);
	    
	    PdfPCell badgeCell = new PdfPCell(new Phrase(type, badgeFont));
	    badgeCell.setBackgroundColor(bgColor);
	    badgeCell.setBorder(Rectangle.NO_BORDER);
	    badgeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    badgeCell.setPaddingTop(3);
	    badgeCell.setPaddingBottom(5);
	    
	    badgeTable.addCell(badgeCell);
	    
	    return badgeTable;
	}

	// 2. Temp Color Logic (Same as before)
	private Font getTempFont(Double temp) {
	    if (temp == null) return FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.BLACK);
	    Color color = (temp >= 35) ? new Color(220, 20, 60) : 
	                  (temp >= 25) ? new Color(255, 140, 0) : 
	                  (temp <= 15) ? new Color(30, 144, 255) : new Color(46, 139, 87);
	    return FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, color);
	}

}
