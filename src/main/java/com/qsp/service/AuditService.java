package com.qsp.service;

import java.util.List;

import com.qsp.entity.Audit;
import com.qsp.responsedto.AuditDto;

public interface AuditService {
	
	void createAudit(String userId, String action, String message);
	
	List<Audit> fetchAuditRecords(Integer pageNumber, Integer pageSize);
}
