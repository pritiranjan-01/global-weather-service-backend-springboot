package com.qsp.modelmapper;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.qsp.responsedto.ResponseStructure;

@Component
public class ResponseStructureModelMapper {

	public <T> ResponseStructure<T> mapToResponseStructure
				(HttpStatus httpStatus, String type, T payload) {
		ResponseStructure<T> structure = ResponseStructure.<T>builder()
				.status(true)
				.payload(payload)
				.statusCode(httpStatus.value())
				.type(type)
				.build();
		return structure;

	}
}
