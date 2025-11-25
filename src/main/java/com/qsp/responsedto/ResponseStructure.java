package com.qsp.responsedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseStructure<T> {
	private Integer statusCode;
	private Boolean status;
	private String type;
	private T payload;
}
