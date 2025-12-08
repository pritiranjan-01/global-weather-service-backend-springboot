package com.qsp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.qsp.entity.Client;
import com.qsp.modelmapper.ResponseEntityMapper;
import com.qsp.modelmapper.ResponseStructureModelMapper;
import com.qsp.requestdto.ClientCreationDto;
import com.qsp.requestdto.ClientUpdateRequestDto;
import com.qsp.responsedto.ResponseStructure;
import com.qsp.service.ClientService;
import com.qsp.service.MailOTPService;
import com.qsp.util.SubscriptionType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/client")
@Tag(name = "/client", description = "Create, Read, Update, Delete Client API")
@Validated
public class ClientController {
	private final ResponseStructureModelMapper responseStructureModelMapper;
	private final ResponseEntityMapper responseEntityMapper;
	private final MailOTPService mailService;
	private final ClientService clientService;
	
	@GetMapping("/subscription-types")
	@Operation(summary = "Get all available Subscriptions")
	public ResponseEntity<ResponseStructure<String[]>> preprocessing(){
		
		String[] payLoad = SubscriptionType.getAllTypes();
		ResponseStructure<String[]> res=  responseStructureModelMapper
				.mapToResponseStructure(HttpStatus.OK, "String", payLoad);
		return responseEntityMapper.getResponseEntity(res,HttpStatus.OK);
	}
	
	@PostMapping("/register")
	@Operation(summary = "Client Creation API")
	public ResponseEntity<ResponseStructure<String>> clientRegistration(
			@Valid @RequestBody ClientCreationDto dto) {
		
		String payload =  mailService.sendOtp(dto.getEmail(), dto);
		ResponseStructure<String> res = responseStructureModelMapper
				.mapToResponseStructure(HttpStatus.OK, "String", payload);
		return responseEntityMapper.getResponseEntity(res, HttpStatus.OK);
	}
	
	@PostMapping("/verify-otp")
	@Operation(summary = "Verify Client Email received OTP")
	public ResponseEntity<ResponseStructure<String>> verifyClientOTP(
			@RequestParam("email") String email,
			@RequestParam("otp") String Otp) {
		
		String payload =  mailService.validateOtp(email, Otp);
		ResponseStructure<String> res = responseStructureModelMapper
				.mapToResponseStructure(HttpStatus.CREATED, "String", payload);
		return responseEntityMapper.getResponseEntity(res, HttpStatus.CREATED);
	}
	
	@Operation(summary = "Delete a Client")
	@DeleteMapping("/{email}")
	public ResponseEntity<ResponseStructure<String>> deleteClient(@PathVariable String email) {
		
		String payload = clientService.deleteClientService(email);
		ResponseStructure<String> res = responseStructureModelMapper
				.mapToResponseStructure(HttpStatus.OK, "String", payload);
		return responseEntityMapper.getResponseEntity(res, HttpStatus.OK);
		
	}
	
	@Operation(summary = "Fetch all the Clients")
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Client>>> getAllClients
				(@RequestParam(defaultValue = "true")  Boolean isActive){
		List<Client> payload =  clientService.getAllClientsServiceByStatus(isActive);
		ResponseStructure<List<Client>> res = responseStructureModelMapper
				.mapToResponseStructure(HttpStatus.OK, "String", payload);
		return responseEntityMapper.getResponseEntity(res, HttpStatus.OK);
	}
	
	@Operation(summary = "Update a Client by Email")
	@PutMapping("/{email}")
	public ResponseEntity<ResponseStructure<Map<String, ClientCreationDto>>> updateClient(
			@PathVariable String email,
			@Valid @RequestBody ClientUpdateRequestDto dto){
		
		Map<String, ClientCreationDto> payload =  clientService.updateClientService(email, dto);
		ResponseStructure<Map<String, ClientCreationDto>> res = responseStructureModelMapper
				.mapToResponseStructure(HttpStatus.OK, "String", payload);
		return responseEntityMapper.getResponseEntity(res, HttpStatus.OK);
	}
	
	@PatchMapping("/{email}/subscription")
	public ResponseEntity<ResponseStructure<String>> updateClientSubscription(
			@PathVariable String email, @RequestParam Integer subscriptionId){
		String response = clientService.updateClientSubscription(email, subscriptionId);
		ResponseStructure<String> payload = 
				responseStructureModelMapper.mapToResponseStructure(HttpStatus.OK, "String", response);
		return responseEntityMapper.getResponseEntity(payload, HttpStatus.OK);
	}
	
	@GetMapping("/{email}")
	public ResponseEntity<ResponseStructure<ClientCreationDto>> getClientByEmail(
		@PathVariable String email){
		Client client = clientService.getClientByEmail(email);
		ClientCreationDto dto = ClientCreationDto.builder()
				                      .name(client.getName())
				                      .phoneNumber(client.getPhoneNumber())
				                      .email(client.getEmail())
				                      .subscriptionType(client.getSubscriptionType().ordinal())
				                      .build();
		 ResponseStructure<ClientCreationDto> payload = 
				 responseStructureModelMapper.mapToResponseStructure(HttpStatus.OK, "Object", dto);
		 return responseEntityMapper.getResponseEntity(payload, HttpStatus.OK);
	}
	
	@GetMapping("/count/{isActive}")
	public  ResponseEntity<ResponseStructure<Map<String, Long>>> getCountOfClients(@PathVariable Boolean isActive){
		Long count = clientService.countOfClientByStatus(isActive);
		ResponseStructure<Map<String, Long>> payload =
				responseStructureModelMapper.mapToResponseStructure(HttpStatus.OK, "Object", Map.of("count",count));
		return responseEntityMapper.getResponseEntity(payload, HttpStatus.OK);
	}
	
	@PatchMapping("/{email}/status")
	public ResponseEntity<ResponseStructure<Map<String, String>>> updateClientActiveStatus(
			@PathVariable String email, @RequestParam Boolean status){
		String message = clientService.updateClientActiveStatusService(email, status);
		ResponseStructure<Map<String, String>> structure = responseStructureModelMapper
							.mapToResponseStructure(HttpStatus.OK, "Object", Map.of("message", message));
		return responseEntityMapper.getResponseEntity(structure, HttpStatus.OK);
	}
	
}
