package com.qsp.service;

public interface AuditService {
	void createAudit(String userId, String action, String message);
}
