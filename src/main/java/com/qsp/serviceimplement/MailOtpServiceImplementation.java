package com.qsp.serviceimplement;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.qsp.event.ClientSaveEvent;
import com.qsp.exception.custom.EmailAlreadyExistException;
import com.qsp.exception.custom.OtpExpiredException;
import com.qsp.exception.custom.OtpGenerationException;
import com.qsp.exception.custom.OtpMismatchException;
import com.qsp.exception.custom.OtpNotGeneratedException;
import com.qsp.exception.custom.TooManyOtpAttemptsException;
import com.qsp.requestdto.ClientCreationDto;
import com.qsp.service.ClientService;
import com.qsp.service.MailOTPService;

import jakarta.mail.internet.MimeMessage;

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
			
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

			String htmlContent = """
			        <p>Dear User,</p>
			        <p>
			            Your One-Time Password (OTP) for creating your 
			            <b>Global Weather Service</b> account is:  <b>%s</b> 
			        </p>
			        <p>Please enter this OTP within <b>2 minutes</b> to complete your verification.</p>
			        <p>If you did not request this OTP, please ignore this email or contact our support team.</p>
			        <p> Regards,<br> <b>Global Weather Service Team</b> </p>
			        """.formatted(otp);

			helper.setTo(emailId);
			helper.setSubject("Your OTP for Global Weather Service");
			helper.setFrom("pritiranjan.mohanty2003@gmail.com");
			helper.setText(htmlContent, true);

			mailSender.send(mimeMessage);
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
		return "OTP validated successfully. Your account has been created. Please check your email.";
	}
}
