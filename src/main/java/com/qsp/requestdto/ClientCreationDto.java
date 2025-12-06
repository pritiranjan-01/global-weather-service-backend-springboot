package com.qsp.requestdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
	
	@NotBlank(message = "Name is required")
	private String name;
	
	@NotBlank(message = "Email is required")
	@Email(message = "Invalid Email address")
	private String email;
	
	@NotNull(message = "Subscription Type is required")
	private Integer subscriptionType;
	
	@NotNull(message = "Phone Number is required")
	@Size(min = 10, max = 10, message = "Mobile number must be 10 digits long")
	@Pattern(regexp = "^[6-9]{1}[0-9]{9}$", message = "Invalid Mobile Number")
	private String phoneNumber;
}
