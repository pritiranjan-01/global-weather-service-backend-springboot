package com.qsp.serviceimplement;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.qsp.entity.Audit;
import com.qsp.repository.AuditRepositiry;
import com.qsp.responsedto.AuditDto;
import com.qsp.service.AuditService;

@Service
public class AuditServiceImplementation implements AuditService{

	@Autowired
	private AuditRepositiry auditRepo;
	
	@Override
	public void createAudit(String userId, String action, String message) {
		Audit audit = Audit.builder()
				.auditId(UUID.randomUUID().toString())
				.userId(userId)
				.action(action)
				.message(message)
				.build();		
		auditRepo.save(audit);
	}

	@Override
	public List<Audit> fetchAuditRecords(Integer pageNumber, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		return auditRepo.findAll(pageable).getContent();
	}

}
