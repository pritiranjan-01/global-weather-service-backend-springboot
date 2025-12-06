package com.qsp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qsp.entity.EmailLogReport;

@Repository
public interface EmailLogRepository extends JpaRepository<EmailLogReport, Integer>{

}
