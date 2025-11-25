package com.qsp.exception.custom;

public class OtpExpiredException extends RuntimeException {
	public OtpExpiredException(String message) {
		super(message);
	}
}
