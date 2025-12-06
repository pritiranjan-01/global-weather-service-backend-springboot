package com.qsp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.qsp.entity.Client;
import java.util.List;
import com.qsp.util.SubscriptionType;



@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
	
	boolean existsByEmail(String email);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE Client c SET c.isActive = :status WHERE c.email = :email")
	int updateClientActiveStatusByEmail(String email, Boolean status);
	
	Optional<Client> findByEmail(String email);
	
	boolean existsByEmailAndIsActiveTrue(String email);

	List<Client> findByIsActive(Boolean status);
	
	List<Client> findBySubscriptionTypeAndIsActiveTrue(SubscriptionType subscriptionType);
	
	Long countByIsActive(Boolean status);
	
}
