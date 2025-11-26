package com.qsp.requestdto;

import jakarta.validation.constraints.Max;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherReportUpdateRequestDTO {

	@NotNull(message = "Temperature is required")
	@Min(value = -100, message = "Temperature can't be below -100°C")
	@Max(value = 100, message = "Temperature can't be above 100°C")
	private Integer temp;

	@NotBlank(message = "description cant be empty and null")
	private String description;
}
