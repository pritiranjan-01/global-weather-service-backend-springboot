package com.qsp.listener;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.qsp.entity.Client;
import com.qsp.event.ClientSaveEvent;
import com.qsp.modelmapper.ClientMapper;
import com.qsp.requestdto.ClientCreationDto;
import com.qsp.service.AuditService;
import com.qsp.service.ClientService;
import com.qsp.util.SubscriptionType;
import com.resend.Resend;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;

//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddClientListener {

	private final ClientService clientService;
	
	private final AuditService auditService;
	
//	private final JavaMailSender mailSender;
	
 	private final Resend resend;
	
	private final ClientMapper clientMapper;
	
	@Autowired
	@Qualifier("otpholder")
	private Map<String, Object[]> otpHolder;


	@Async
	@Order(2)
	@EventListener
	public void notifyClient(ClientSaveEvent event) {

	    Object[] object = otpHolder.get(event.getEmailid());
	    ClientCreationDto dto = (ClientCreationDto) object[0];

	    String name = dto.getName();
	    SubscriptionType subscriptionType = SubscriptionType.getUserSubscriptionType(dto.getSubscriptionType());
	    String subscriptionDescription = SubscriptionType.getSubscriptionDetails(subscriptionType);
	
	    // Prepare MIME message
//	    MimeMessage mimeMessage = mailSender.createMimeMessage();
//	    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
//	    helper.setFrom("pritiranjan.mohanty2003@gmail.com");
//	    helper.setTo(event.getEmailid());
//	    helper.setSubject("Welcome to Global Weather Service India 🎉");

	    // HTML Message (simple and clean)
	    String htmlMessage = ""
	        + "<html>"
	        + "<body style='font-family: Arial, sans-serif; font-size: 15px; color: #2d3748;'>"

	        + "<p>Dear <strong>" + name + "</strong>,</p>"

	        + "<p>Thank you for using Global Weather Service India. We're excited to have you with us!</p>"

	        + "<p>You are currently subscribed to the following plan: </p>"
	        
	        + "<span style='color:#1a73e8; font-weight:bold;'>" + subscriptionType + "</span>"

	        + "<p>" + subscriptionDescription + "</p>"

	        + "<p>We appreciate your trust in our service. Stay tuned for accurate and timely weather updates.</p>"

	        + "<p>Regards,<br>"
	        + "<strong>Global Weather Service India Team</strong></p>"

	        + "</body>"
	        + "</html>";

//	    helper.setText(htmlMessage, true);
//	    mailSender.send(mimeMessage);
	    
	    try {
	        CreateEmailOptions params = CreateEmailOptions.builder()
			        .from("Weather App <weather@pritiranjan.dev>")
			        .to(event.getEmailid())
			        .subject("Welcome to Global Weather Service India 🎉")
			        .html(htmlMessage)
			        .build();

			CreateEmailResponse response = resend.emails().send(params);
	        }catch (Exception e) {
	        		e.printStackTrace();
			}
	}

	
	@Async
	@Order(3)
	@EventListener
	public void auditClient(ClientSaveEvent event){
		auditService.createAudit(event.getEmailid(),"REGISTER","New User subscribed");
		otpHolder.remove(event.getEmailid());
     // System.out.println("OTP Holder: "+ otpHolder);
	}
	
	@Async
	@Order(1)
	@EventListener
	public void createClient(ClientSaveEvent event) {
		Object[] object = otpHolder.get(event.getEmailid());
		ClientCreationDto clientDTO = (ClientCreationDto)object[0];
		Client client = clientMapper.clientCreationtDTOToClient(clientDTO, new Client());
		clientService.saveClientService(client);
	}
}
