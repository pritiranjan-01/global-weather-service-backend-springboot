package com.qsp.requestdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientCreationDto {
	private String name;
	private String email;
	private Integer subscriptionType;
	private Long phoneNumber;
}
