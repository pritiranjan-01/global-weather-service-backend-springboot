package com.qsp.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class WeatherCreationDto {
	@NotBlank(message = "city cant be blank")
	private String city;
	@NotNull(message = "Temperature is required")
	private Integer temp;
	@NotBlank(message = "WeatherType cant be blank")
	private String weatherType;
	
}
