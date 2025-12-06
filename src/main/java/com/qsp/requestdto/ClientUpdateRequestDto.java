package com.qsp.requestdto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientUpdateRequestDto {
	
	@NotBlank(message = "Name cant be blank")
	private String name;
	
	@NotNull(message = "Mobile Number is required")
	private String mobileNumber;
	
//	@NotNull(message = "Subscription type is required")
//	private Integer subscrptionType;
}
