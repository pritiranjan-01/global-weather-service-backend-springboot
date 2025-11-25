package com.qsp.requestdto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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

	@NotNull(message = "Temperature can't be null")
	@Min(value = -100, message = "Temperature can't be below -100°C")
	@Max(value = 100, message = "Temperature can't be above 100°C")
	private Integer temp;

	@NotEmpty(message = "description cant be blank")
	@NotNull(message = "description cant be null")
	private String description;
}
