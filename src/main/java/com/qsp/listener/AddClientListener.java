package com.qsp.listener;

import java.util.Map;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.qsp.entity.Audit;
import com.qsp.entity.Client;
import com.qsp.event.ClientSaveEvent;
import com.qsp.exception.handler.GlobalExceptionHandler;
import com.qsp.modelmapper.ClientMapper;
import com.qsp.repository.AuditRepositiry;
import com.qsp.repository.ClientRepository;
import com.qsp.requestdto.ClientCreationDto;
import com.qsp.service.AuditService;
import com.qsp.service.ClientService;
import com.qsp.util.SubscriptionType;

@Component
public class AddClientListener {

    private final GlobalExceptionHandler globalExceptionHandler;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private AuditService auditService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private ClientMapper clientMapper;
	
	@Autowired
	@Qualifier("otpholder")
	private Map<String, Object[]> otpHolder;

    AddClientListener(GlobalExceptionHandler globalExceptionHandler) {
        this.globalExceptionHandler = globalExceptionHandler;
    }

	@Order(2)
	@EventListener
	@Async
	public void notifyClient(ClientSaveEvent event) {
		SimpleMailMessage message =  new SimpleMailMessage();
		
		Object object[] = otpHolder.get(event.getEmailid());
		String name = ((ClientCreationDto)object[0]).getName();
		message.setFrom("pritiranjan.mohanty2003@gmail.com");
		message.setTo(event.getEmailid());
		message.setSubject("Notification from Weather Service India");
		
		StringBuilder builder = new StringBuilder();
		builder.append("Dear "+name + "\n\nThanks for using our Service.\n\nRegards,\nWeather Service India");
		message.setText(builder.toString());
		mailSender.send(message);
	}
	
	@Order(3)
	@EventListener
	@Async
	public void auditClient(ClientSaveEvent event){
		auditService.createAudit(event.getEmailid(),"REGISTER","New User subscribed");
		otpHolder.remove(event.getEmailid());
		System.out.println("OTP Holder: "+ otpHolder);
	}
	
	@Order(1)
	@EventListener
	@Async
	public void createClient(ClientSaveEvent event) {
		Object[] object = otpHolder.get(event.getEmailid());
		ClientCreationDto clientDTO = (ClientCreationDto)object[0];
		Client client = clientMapper.clientCreationtDTOToClient(clientDTO, new Client());
		clientService.saveClientService(client);
	}
}
