package com.qsp.exception.custom;

public class TooManyOtpAttemptsException extends RuntimeException{
	public TooManyOtpAttemptsException(String message) {
		super(message);
	}
}
