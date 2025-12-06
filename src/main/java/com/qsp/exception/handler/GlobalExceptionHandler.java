package com.qsp.exception.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.qsp.exception.custom.EmailAlreadyExistException;
import com.qsp.exception.custom.InvalidSubscriptionException;
import com.qsp.exception.custom.OtpExpiredException;
import com.qsp.exception.custom.OtpMismatchException;
import com.qsp.exception.custom.OtpNotGeneratedException;
import com.qsp.exception.custom.ServiceUnavailableException;
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
	
	@ExceptionHandler(MethodArgumentNotValidException.class)   // This is for request body
	public ResponseEntity<Map<String,String>> handleRequestBodyValidationErrors
			(MethodArgumentNotValidException ex){
		 Map<String, String> errors = new HashMap<>();
		 ex.getBindingResult().getFieldErrors().forEach(error->{
			 		errors.put(error.getField(), error.getDefaultMessage());
		 });
		 
		 return new ResponseEntity<> (errors,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class) // for extra fields in request body
	public ResponseEntity<Map<String, Object>> handleJsonParseErrors(HttpMessageNotReadableException ex) {

	    Throwable cause = ex.getCause();

	    // If the cause is unknown property → return custom message
	    if (cause instanceof UnrecognizedPropertyException unrecognized) {
	        Map<String, Object> error = new HashMap<>();
	        error.put("error", "Unknown field in request body");
	        error.put("field", unrecognized.getPropertyName());
	        return ResponseEntity.badRequest().body(error);
	    }

	    // fallback for other JSON parse errors
	    Map<String, Object> err = new HashMap<>();
	    err.put("error", "Invalid JSON format");
	    return ResponseEntity.badRequest().body(err);
	}

	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ResponseStructure<String>> handelNoSuchElementException(NoSuchElementException ex){
		ResponseStructure<String> structure = responseStructureMapper
				    .mapToResponseStructure(HttpStatus.NOT_FOUND, "String", ex.getMessage());
		structure.setStatus(false);
		return responseEntityMapper.getResponseEntity(structure, HttpStatus.NOT_FOUND);
		
	}
	
	
	@ExceptionHandler(OtpExpiredException.class)
	public ResponseEntity<ResponseStructure<String>>  handleOtpExpired(OtpExpiredException e){
		ResponseStructure<String> structure = responseStructureMapper
			    .mapToResponseStructure(HttpStatus.GONE, "String", e.getMessage());
		structure.setStatus(false);
		return responseEntityMapper.getResponseEntity(structure, HttpStatus.GONE);

	}
	@ExceptionHandler(OtpMismatchException.class)
	public ResponseEntity<ResponseStructure<String>>  handleOtpMismatch(OtpMismatchException e){
		ResponseStructure<String> structure = responseStructureMapper
			    .mapToResponseStructure(HttpStatus.UNAUTHORIZED, "String", e.getMessage());
		structure.setStatus(false);
		return responseEntityMapper.getResponseEntity(structure, HttpStatus.UNAUTHORIZED);

	}
	@ExceptionHandler(OtpNotGeneratedException.class)
	public ResponseEntity<ResponseStructure<String>>  handleOtpNotGenerated(OtpNotGeneratedException e){
		ResponseStructure<String> structure = responseStructureMapper
			    .mapToResponseStructure(HttpStatus.BAD_REQUEST, "String", e.getMessage());
		structure.setStatus(false);
		return responseEntityMapper.getResponseEntity(structure, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(EmailAlreadyExistException.class)
	public ResponseEntity<?> handleEmailAlreadyException(EmailAlreadyExistException e){
		ResponseStructure<String> structure = responseStructureMapper
			    .mapToResponseStructure(HttpStatus.ALREADY_REPORTED, "String", e.getMessage());
		structure.setStatus(false);
		return responseEntityMapper.getResponseEntity(structure, HttpStatus.ALREADY_REPORTED);

	}
	@ExceptionHandler(InvalidSubscriptionException.class)
	public ResponseEntity<ResponseStructure<String>>  handleInvalidSubscriptionException(InvalidSubscriptionException e){
		ResponseStructure<String> structure = responseStructureMapper
			    .mapToResponseStructure(HttpStatus.BAD_REQUEST, "String", e.getMessage());
		structure.setStatus(false);
		return responseEntityMapper.getResponseEntity(structure, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(TooManyOtpAttemptsException.class)
	public ResponseEntity<?> handleTooManyOtpAttemptsException(TooManyOtpAttemptsException e){
		ResponseStructure<String> structure = responseStructureMapper
			    .mapToResponseStructure(HttpStatus.BAD_REQUEST, "String", e.getMessage());
		structure.setStatus(false);
		return responseEntityMapper.getResponseEntity(structure, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ResponseStructure<String>>  handleTooManyOtpAttemptsException(RuntimeException e){
		ResponseStructure<String> structure = responseStructureMapper
			    .mapToResponseStructure(HttpStatus.EXPECTATION_FAILED, "String", e.getMessage());
		structure.setStatus(false);
		return responseEntityMapper.getResponseEntity(structure, HttpStatus.EXPECTATION_FAILED);
	}
	@ExceptionHandler(ServiceUnavailableException.class)
	public ResponseEntity<ResponseStructure<String>>  handleTooManyOtpAttemptsException(ServiceUnavailableException e){
		ResponseStructure<String> structure = responseStructureMapper
			    .mapToResponseStructure(HttpStatus.SERVICE_UNAVAILABLE, "String", e.getMessage());
		structure.setStatus(false);
		return responseEntityMapper.getResponseEntity(structure, HttpStatus.SERVICE_UNAVAILABLE);
	}
}