package com.qsp.responsedto;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditDto {
	@NonNull
	private String auditId;
	@NonNull
	private String userEmail;
	@NonNull
	private String action;
	@CreatedDate
	private LocalDateTime createdDateTime;

}
