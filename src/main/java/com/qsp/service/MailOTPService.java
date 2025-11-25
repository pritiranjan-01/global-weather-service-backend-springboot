package com.qsp.service;

import com.qsp.requestdto.ClientCreationDto;

public interface MailOTPService {
	String sendOtp(String emailId, ClientCreationDto dto);

	String validateOtp(String emailId, String otp);
}
