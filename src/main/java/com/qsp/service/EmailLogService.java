package com.qsp.service;

import com.qsp.entity.EmailLogReport;

public interface EmailLogService {
	
	EmailLogReport saveEmailLog(EmailLogReport log);
	
	EmailLogReport getEmailLogReport(Integer id);
}
