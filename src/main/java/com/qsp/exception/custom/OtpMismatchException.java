package com.qsp.exception.custom;

public class OtpMismatchException extends RuntimeException {
	public OtpMismatchException(String message) {
		super(message);
	}
}
