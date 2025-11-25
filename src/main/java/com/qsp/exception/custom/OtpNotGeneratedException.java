package com.qsp.exception.custom;

public class OtpNotGeneratedException extends RuntimeException{
	public OtpNotGeneratedException(String message) {
		super(message);
	}
}
