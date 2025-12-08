package com.qsp.modelmapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.qsp.entity.Audit;
import com.qsp.responsedto.AuditDto;

@Component
public class AudtiDtoMapper {
	
	public List<AuditDto> mapToAuditDto(List<Audit> auditList) {
		return auditList.stream()
				 .map(this::getAuditDto)
				 .collect(Collectors.toList());
		
	}
	
	private AuditDto getAuditDto(Audit audit) {
		return AuditDto.builder()
							.auditId(audit.getAuditId())
							.createdDateTime(audit.getTime())
							.userEmail(audit.getUserId())
							.action(audit.getAction())
							.build();
	}
}
