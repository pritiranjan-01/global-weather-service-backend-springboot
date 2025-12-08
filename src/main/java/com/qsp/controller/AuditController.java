package com.qsp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qsp.entity.Audit;
import com.qsp.modelmapper.AudtiDtoMapper;
import com.qsp.modelmapper.ResponseEntityMapper;
import com.qsp.modelmapper.ResponseStructureModelMapper;
import com.qsp.responsedto.AuditDto;
import com.qsp.responsedto.ResponseStructure;
import com.qsp.service.AuditService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/audit")
@RequiredArgsConstructor
@Tag(name = "/audit", description = "Audit Reports")
public class AuditController {
	
	private final AuditService auditService;
	private final AudtiDtoMapper auditDtoMapper;
	private final ResponseStructureModelMapper structureModelMapper;
	private final ResponseEntityMapper responseEntityMapper;
	
	@GetMapping("/report")
	public ResponseEntity<ResponseStructure<List<AuditDto>>> getAuditReport(
			@RequestParam(required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(required = false, defaultValue = "0") Integer pageNumber){
		
		List<Audit> serviceResponse = auditService.fetchAuditRecords(pageNumber, pageSize);
		List<AuditDto> auditDto = auditDtoMapper.mapToAuditDto(serviceResponse);
		ResponseStructure<List<AuditDto>> structure = structureModelMapper.
							mapToResponseStructure(HttpStatus.OK, "Array", auditDto);
		return responseEntityMapper.getResponseEntity(structure,HttpStatus.OK);
	
	}
}
