package com.qsp.service;

import java.util.List;
import java.util.Map;

import com.qsp.entity.Client;
import com.qsp.requestdto.ClientCreationDto;
import com.qsp.requestdto.ClientUpdateRequestDto;

public interface ClientService {
	
	void saveClientService(Client dto);
	
	String deleteClientService(String email);
	
	Map<String, ClientCreationDto> updateClientService(String Email, ClientUpdateRequestDto dto);
	
	List<Client> getAllClientsServiceByStatus(Boolean status);
	
	boolean checkClientEmailExistAndIsActiveStatusTrue(String email);
	
	String updateClientSubscription(String email,Integer subscriptionCode);
	
	Client getClientByEmail(String email);
	
	Long countOfClientByStatus(Boolean status);
}
