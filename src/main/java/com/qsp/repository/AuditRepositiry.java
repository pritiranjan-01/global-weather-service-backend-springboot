package com.qsp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qsp.entity.Audit;

@Repository
public interface AuditRepositiry extends JpaRepository<Audit, String> {

}
