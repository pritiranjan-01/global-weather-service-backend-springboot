package com.qsp.serviceimplement;

import java.time.LocalDateTime;


import java.util.Map;
import java.util.Random;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.qsp.aspect.WeatherReportAspect;
import com.qsp.event.ClientSaveEvent;
import com.qsp.exception.custom.EmailAlreadyExistException;
import com.qsp.exception.custom.OtpExpiredException;
import com.qsp.exception.custom.OtpGenerationException;
import com.qsp.exception.custom.OtpMismatchException;
import com.qsp.exception.custom.OtpNotGeneratedException;
import com.qsp.exception.custom.TooManyOtpAttemptsException;
import com.qsp.repository.AuditRepositiry;
import com.qsp.repository.ClientRepository;
import com.qsp.requestdto.ClientCreationDto;
import com.qsp.service.ClientService;
import com.qsp.service.MailOTPService;

@Service
public class MailOtpServiceImplementation implements MailOTPService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	@Qualifier("otpholder")
	private Map<String, Object[]> otpHolder;

	@Autowired
	private Random random;

	@Override
	public String sendOtp(String emailId, ClientCreationDto dto) {
		
		if(clientService.checkClientEmailExistAndIsActiveStatusTrue(emailId))
			throw new EmailAlreadyExistException("Email already exist");
		
		try {
			Integer otp = random.nextInt(100000,1000000);
			
			// For Every Email we will keep an array which contains DTO, OTP, OtpExpireTime, InvalidOTP counter
			Object value[] = new Object[] { dto, otp + "", LocalDateTime.now().plusMinutes(3), 0 };
			otpHolder.put(emailId, value);
			
			SimpleMailMessage message =  new SimpleMailMessage();
			message.setFrom("pritiranjan.mohanty2003@gmail.com");
			message.setTo(emailId);
			message.setText("Your One Time Password (OTP) is : "+otp);
			message.setSubject("One Time Password From Weather App");
			mailSender.send(message);
			
			System.out.println(otpHolder);
			
			return  "Otp send successfully to recipient email id";
			
		} catch (Exception e) {
			throw new OtpGenerationException("Invalid Email Id");
		}
	}

	@Override
	public String validateOtp(String emailId, String userOtp) {

		Object[] value = otpHolder.get(emailId);

		if (value == null)
			throw new OtpNotGeneratedException("OTP is not generated yet. Please request a new OTP.");
		
		if (LocalDateTime.now().isAfter((LocalDateTime) value[2]))
			throw new OtpExpiredException("OTP has expired. Please request a new OTP.");
		
		if ((Integer)value[3] >=4) {
			otpHolder.remove(emailId);
			throw new TooManyOtpAttemptsException("You have tried multiple wrong attemps. Try requesting a new OTP");
		}
		
		String inmemory = (String) value[1];

		if (!userOtp.trim().equals(inmemory)) {
			value[3]= (Integer)value[3] + 1;
			otpHolder.replace(emailId, value);
			throw new OtpMismatchException("Incorrect OTP.");
		}

		publisher.publishEvent(new ClientSaveEvent(emailId));
		return "OTP validated successfully";
	}
}
