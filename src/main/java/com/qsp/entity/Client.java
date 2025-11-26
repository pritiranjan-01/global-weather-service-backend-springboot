package com.qsp.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.qsp.util.SubscriptionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
@EntityListeners(AuditingEntityListener.class)
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	
	@NonNull
	private String name;

	@NonNull
	@Column(unique = true)
	private String email;
	
	@NonNull
	private String phoneNumber;
	
	@NonNull
	@Enumerated(EnumType.STRING)
	private SubscriptionType subscriptionType;
	
	@NonNull
	
	private Boolean isActive;
	
	@CreatedDate
	@Column(unique = false)
	private LocalDateTime createdAt;
	
	@LastModifiedDate
	private LocalDateTime lastUpdateAt;
}
