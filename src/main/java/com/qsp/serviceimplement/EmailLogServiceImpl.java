package com.qsp.serviceimplement;

import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;

import com.qsp.entity.EmailLogReport;
import com.qsp.repository.EmailLogRepository;
import com.qsp.service.EmailLogService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailLogServiceImpl  implements EmailLogService{

	private final EmailLogRepository logRepo;
	
	@Override
	public EmailLogReport saveEmailLog(EmailLogReport log) {
		return logRepo.save(log);
	}

	@Override
	public EmailLogReport getEmailLogReport(Integer id) {
		Optional<EmailLogReport> optional = logRepo.findById(id);
		if(optional.isPresent())
			return optional.get();
		throw new RuntimeException("No report found for id "+id);
	}

}
