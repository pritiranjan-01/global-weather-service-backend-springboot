package com.qsp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/client")
@Tag(name = "/client", description = "Client APIs")
public class ClientController {
	private final ResponseStructureModelMapper responseStructureModelMapper;
	private final ResponseEntityMapper responseEntityMapper;
	private final MailOTPService mailService;
	private final ClientService clientService;
	
	@GetMapping("/getAllClientSubscriptionsTypes")
	@Operation(summary = "Get all available Subscriptions")
	public ResponseEntity<ResponseStructure<String[]>> preprocessing(){
		
		String[] payLoad = SubscriptionType.getAllTypes();
		ResponseStructure<String[]> res=  responseStructureModelMapper
				.mapToResponseStructure(HttpStatus.OK, "String", payLoad);
		return responseEntityMapper.getResponseEntity(res,HttpStatus.OK);
	}
	
	@PostMapping("/createClient")
	@Operation(summary = "Client Creation API")
	public ResponseEntity<ResponseStructure<String>> clientRegistration(
			@RequestBody ClientCreationDto dto) {
		
		String payload =  mailService.sendOtp(dto.getEmail(), dto);
		ResponseStructure<String> res = responseStructureModelMapper
				.mapToResponseStructure(HttpStatus.OK, "String", payload);
		return responseEntityMapper.getResponseEntity(res, HttpStatus.OK);
	}
	
	@PostMapping("/verifyClientOtp")
	@Operation(summary = "Verify Client received OTP")
	public ResponseEntity<ResponseStructure<String>> verifyClientOTP(
			@RequestParam("email") String email,
			@RequestParam("otp") String Otp) {
		
		String payload =  mailService.validateOtp(email, Otp);
		ResponseStructure<String> res = responseStructureModelMapper
				.mapToResponseStructure(HttpStatus.CREATED, "String", payload);
		return responseEntityMapper.getResponseEntity(res, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{email}")
	public ResponseEntity<ResponseStructure<String>> deleteClient(@PathVariable String email) {
		
		String payload = clientService.deleteClientService(email);
		ResponseStructure<String> res = responseStructureModelMapper
				.mapToResponseStructure(HttpStatus.OK, "String", payload);
		return responseEntityMapper.getResponseEntity(res, HttpStatus.OK);
		
	}
	
	@GetMapping("/getAllClientsByStatus")
	public ResponseEntity<ResponseStructure<List<Client>>> getAllClients
				(@RequestParam Boolean isActive){
		List<Client> payload =  clientService.getAllClientsServiceByStatus(isActive);
		ResponseStructure<List<Client>> res = responseStructureModelMapper
				.mapToResponseStructure(HttpStatus.OK, "String", payload);
		return responseEntityMapper.getResponseEntity(res, HttpStatus.OK);
	}
	
	@PutMapping("/{email}")
	public ResponseEntity<ResponseStructure<Map<String, ClientCreationDto>>> updateClient(
			@PathVariable String email,
			@RequestBody ClientUpdateRequestDto dto){
		
		Map<String, ClientCreationDto> payload =  clientService.updateClientService(email, dto);
		ResponseStructure<Map<String, ClientCreationDto>> res = responseStructureModelMapper
				.mapToResponseStructure(HttpStatus.OK, "String", payload);
		return responseEntityMapper.getResponseEntity(res, HttpStatus.OK);
	}
	
	
}
