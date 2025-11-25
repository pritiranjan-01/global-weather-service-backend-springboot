package com.qsp.modelmapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.qsp.responsedto.ResponseStructure;

@Component
public class ResponseEntityMapper {
	public <T> ResponseEntity<ResponseStructure<T>> getResponseEntity(
				 ResponseStructure<T> payload, 
			     HttpStatus status) {
		
		return new ResponseEntity<ResponseStructure<T>>(payload, status);
	}
}
