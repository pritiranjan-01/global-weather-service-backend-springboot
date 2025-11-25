package com.qsp.exception.custom;

public class EmailAlreadyExistException extends RuntimeException{
	public EmailAlreadyExistException(String message) {
		super(message);
	}
}
