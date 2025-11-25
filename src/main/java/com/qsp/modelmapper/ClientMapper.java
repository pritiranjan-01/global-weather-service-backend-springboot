package com.qsp.modelmapper;

import org.springframework.stereotype.Component;
import com.qsp.entity.Client;
import com.qsp.requestdto.ClientCreationDto;
import com.qsp.util.SubscriptionType;

@Component
public class ClientMapper {
	
	public Client clientCreationtDTOToClient(ClientCreationDto dto, Client client) {
		client.setEmail(dto.getEmail());
		client.setIsActive(true);
		client.setName(dto.getName());
		client.setPhoneNumber(dto.getPhoneNumber());
		client.setSubscriptionType(SubscriptionType.getUserSubscriptionType(dto.getSubscriptionType()));
		return client;
	}
}
