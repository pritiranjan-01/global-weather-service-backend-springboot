package com.qsp.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.qsp.event.ClientSubscriptionUpdateEvent;
import com.qsp.service.AuditService;
import com.qsp.util.SubscriptionType;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class ClientSubscriptionUpdateListener {

	@Autowired
	private AuditService auditService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Async
	@EventListener
	public void notifySubscriptionChanges(ClientSubscriptionUpdateEvent event) throws MessagingException {

	    SubscriptionType subscriptionType = SubscriptionType.getUserSubscriptionType(event.getSubscriptionCode());
	    String subscriptionDescription = SubscriptionType.getSubscriptionDetails(subscriptionType);

	    // Create Mime Message
	    MimeMessage mimeMessage = mailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

	    helper.setFrom("pritiranjan.mohanty2003@gmail.com");
	    helper.setTo(event.getEmail());
	    helper.setSubject("Your Subscription Has Been Updated 🎉");

	    String htmlMessage = ""
	        + "<html>"
	        + "<body style='font-family: Arial, sans-serif; font-size: 15px; color: #2d3748;'>"

	        + "<p>Hello <strong>" + event.getName() + "</strong>,</p>"

	        + "<p>Great news! Your subscription has been successfully updated.</p>"

	        + "<p><strong>Your new plan:</strong> "
	        + "<span style='color:#1a73e8; font-weight:bold;'>" + subscriptionType + "</span></p>"

	        + "<p>" + subscriptionDescription + "</p>"

	        + "<p>If you have any questions or need assistance, feel free to reach out.</p>"

	        + "<p>Thanks for staying with us!<br>"
	        + "<strong>— Global Weather Service India</strong></p>"

	        + "</body>"
	        + "</html>";

	    helper.setText(htmlMessage, true);  // true => HTML email

	    mailSender.send(mimeMessage);
	}
	
	@Async
	@EventListener
	public void auditNewSubscriptionOfClient(ClientSubscriptionUpdateEvent event){
		auditService.createAudit(event.getEmail(),"SUBSCRIPTION UPDATED","Updated Subscription is "
						+SubscriptionType.getUserSubscriptionType(event.getSubscriptionCode()).name());
	}
}
