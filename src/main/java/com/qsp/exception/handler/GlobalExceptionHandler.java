package com.qsp.exception.handler;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.qsp.exception.custom.EmailAlreadyExistException;
import com.qsp.exception.custom.InvalidSubscriptionException;
import com.qsp.exception.custom.OtpExpiredException;
import com.qsp.exception.custom.OtpMismatchException;
import com.qsp.exception.custom.OtpNotGeneratedException;
import com.qsp.exception.custom.TooManyOtpAttemptsException;
import com.qsp.modelmapper.ResponseEntityMapper;
import com.qsp.modelmapper.ResponseStructureModelMapper;
import com.qsp.responsedto.ResponseStructure;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@Autowired
	private ResponseStructureModelMapper responseStructureMapper;
	
	@Autowired
	private ResponseEntityMapper responseEntityMapper;
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ResponseStructure<String>> handelNoSuchElementException(NoSuchElementException ex){
		ResponseStructure<String> structure = responseStructureMapper
				    .mapToResponseStructure(HttpStatus.NOT_FOUND, "String", ex.getMessage());
		structure.setStatus(false);
		return responseEntityMapper.getResponseEntity(structure, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(OtpExpiredException.class)
	public ResponseEntity<?> handleOtpExpired(OtpExpiredException e){
		return ResponseEntity
				.status(HttpStatus.GONE)
				.body(e.getMessage());
	}
	@ExceptionHandler(OtpMismatchException.class)
	public ResponseEntity<?> handleOtpMismatch(OtpMismatchException e){
		return ResponseEntity
				.status(HttpStatus.UNAUTHORIZED)
				.body(e.getMessage());
	}
	@ExceptionHandler(OtpNotGeneratedException.class)
	public ResponseEntity<?> handleOtpNotGenerated(OtpNotGeneratedException e){
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(e.getMessage());
	}
	@ExceptionHandler(EmailAlreadyExistException.class)
	public ResponseEntity<?> handleEmailAlreadyException(EmailAlreadyExistException e){
		return ResponseEntity
				.status(HttpStatus.ALREADY_REPORTED)
				.body(e.getMessage());
	}
	@ExceptionHandler(InvalidSubscriptionException.class)
	public ResponseEntity<?> handleInvalidSubscriptionException(InvalidSubscriptionException e){
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(e.getMessage());
	}
	@ExceptionHandler(TooManyOtpAttemptsException.class)
	public ResponseEntity<?> handleTooManyOtpAttemptsException(TooManyOtpAttemptsException e){
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(e.getMessage());
	}
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> handleTooManyOtpAttemptsException(RuntimeException e){
		return ResponseEntity
				.status(HttpStatus.EXPECTATION_FAILED)
				.body(e.getMessage());
	}
}