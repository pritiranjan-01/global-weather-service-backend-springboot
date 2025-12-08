package com.qsp.serviceimplement;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.qsp.entity.Client;
import com.qsp.event.ClientSubscriptionUpdateEvent;
import com.qsp.repository.ClientRepository;
import com.qsp.requestdto.ClientCreationDto;
import com.qsp.requestdto.ClientUpdateRequestDto;
import com.qsp.service.ClientService;
import com.qsp.util.SubscriptionType;

@Service
public class ClientServiceImplementation implements ClientService{

	@Autowired
	private ClientRepository clientRepo;
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Override
	public void saveClientService(Client client) {
		Optional<Client> opt = clientRepo.findByEmail(client.getEmail());
	     if (opt.isPresent()) {
	            Client existing = opt.get();
	            existing.setIsActive(true);
	            existing.setName(client.getName());
	            existing.setPhoneNumber(client.getPhoneNumber());
	            existing.setSubscriptionType(client.getSubscriptionType());
	            clientRepo.save(existing);
	      }
	     else {
	    	 	clientRepo.save(client);
	     }
	}

	@Transactional
	@Override
	public String deleteClientService(String email) {
		if(!clientRepo.existsByEmail(email))
			throw new NoSuchElementException("No Client was found with email "+email);
		clientRepo.updateClientActiveStatusByEmail(email, false);
		return "Client deleted successfully";
	}

	@Transactional
	@Override
	public Map<String, ClientCreationDto> updateClientService(String email, ClientUpdateRequestDto dto) {
		Optional<Client> optionalClient = clientRepo.findByEmail(email);
		if(optionalClient.isEmpty())
			throw new NoSuchElementException("No Client was found with email "+email);
		Client client = optionalClient.get();
		client.setIsActive(true);
		client.setName(dto.getName());
		client.setPhoneNumber(dto.getMobileNumber());
	
		ClientCreationDto updatedData = ClientCreationDto.builder()
												.email(email)
												.name(dto.getName())
												.phoneNumber(dto.getMobileNumber())
												.subscriptionType(client.getSubscriptionType().ordinal())
												.build();
		
		clientRepo.save(client);
		return Map.of("UpdatedData",updatedData);
	}

	@Override
	public List<Client> getAllClientsServiceByStatus(Boolean status) {
		 List<Client> clients = clientRepo.findByIsActive(status);
		 if(clients.size()==0)
			 throw new RuntimeException("No Clients are present with status "+status);
		 return clients;
	}
	

	@Override
	public boolean checkClientEmailExistAndIsActiveStatusTrue(String email) {
		return clientRepo.existsByEmailAndIsActiveTrue(email);
	}

	@Transactional
	@Override
	public String updateClientSubscription(String email, Integer subscriptionCode) {
		Optional<Client> optional = clientRepo.findByEmail(email);
		if(optional.isEmpty()) 	throw new NoSuchElementException("No Client was found with email "+email);
		if(!optional.get().getIsActive()) throw new NoSuchElementException("Client is inactive");
        Client client = optional.get();
        client.setSubscriptionType(SubscriptionType.getUserSubscriptionType(subscriptionCode));
        clientRepo.save(client);
        
        publisher.publishEvent(new ClientSubscriptionUpdateEvent(client.getName(), email, subscriptionCode)); // call to listener 
        
        return "Client Subscription updated";
	}

	@Override
	public Client getClientByEmail(String email) {
		Optional<Client> optional = clientRepo.findByEmail(email);
		if(optional.isEmpty()) 	throw new NoSuchElementException("No Client was found with email ");
		if(!optional.get().getIsActive()) throw new NoSuchElementException("Client is inactive");
		return optional.get();
	}

	@Override
	public Long countOfClientByStatus(Boolean status) {
		return clientRepo.countByIsActive(status);
	}

	@Transactional
	@Override
	public String updateClientActiveStatusService(String email, Boolean status) {
		clientRepo.updateClientActiveStatusByEmail(email, status);
		return "Client active status updated";
	}
}
