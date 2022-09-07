package com.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.booking.model.dto.ChatDTO;
import com.booking.service.ChatService;
import com.booking.util.CommonMethods;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class ChatController {

	@Autowired
	private ChatService chatService;

	@PostMapping("chat")
	@ResponseBody
	@PreAuthorize("hasAnyRole('CLIENT','ADVISOR')")
	@Operation(summary = "send chat message - ROLE ADVISOR/CLIENT", operationId = "", description = "receiverId*,message* ")
	public ResponseEntity<?> sendMessage(@RequestBody ChatDTO request) {
		Long userId = CommonMethods.getUserPrincipal().getId();
		request.setSenderId(userId);
		chatService.sendMessage(request);
		return ResponseEntity.ok(null);
	}

	@GetMapping("chat/{nextUserId}")
	@ResponseBody
	@PreAuthorize("hasAnyRole('CLIENT','ADVISOR')")
	@Operation(summary = "get chat message - ROLE ADVISOR/CLIENT", operationId = "", description = "receiverId*,message* ")
	public ResponseEntity<?> getMessages(@PathVariable("nextUserId") Long nextUserId,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "50") Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Long userId = CommonMethods.getUserPrincipal().getId();
		return ResponseEntity.ok(chatService.getMessages(userId, nextUserId, pageable));
	}

	@GetMapping("chatHeads")
	@ResponseBody
	@PreAuthorize("hasAnyRole('CLIENT','ADVISOR')")
	@Operation(summary = "send chat message - ROLE ADVISOR/CLIENT", operationId = "", description = "receiverId*,message* ")
	public ResponseEntity<?> getChatHeads(
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "50") Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Long userId = CommonMethods.getUserPrincipal().getId();
		return ResponseEntity.ok(chatService.getChatHeads(userId, pageable));
	}

//	@GetMapping("chatUserInfo/{userId}")
//	@ResponseBody
//	@PreAuthorize("hasAnyRole('CLIENT','ADVISOR')")
//	@Operation(summary = "send chat message - ROLE ADVISOR/CLIENT", operationId = "", description = "receiverId*,message* ")
//	public ResponseEntity<?> gethatUserInfo(
//			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "50") Integer pageSize) {
//		Pageable pageable = PageRequest.of(pageNo, pageSize);
//		Long userId = CommonMethods.getUserPrincipal().getId();
//		return ResponseEntity.ok(chatService.getChatHeads(userId, pageable));
//	}

	@GetMapping("clients")
	@ResponseBody
	@PreAuthorize("hasAnyRole('CLIENT','ADVISOR')")
	@Operation(summary = "get Clinets - ROLE ADVISOR/CLIENT", operationId = "", description = "receiverId*,message* ")
	public ResponseEntity<?> getClients(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "50") Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		return ResponseEntity.ok(chatService.getClients(pageable));
	}

	@GetMapping("advisors")
	@ResponseBody
	@PreAuthorize("hasAnyRole('CLIENT','ADVISOR')")
	@Operation(summary = "get Advisors - ROLE ADVISOR/CLIENT", operationId = "", description = "receiverId*,message* ")
	public ResponseEntity<?> getAdvisors(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "50") Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		return ResponseEntity.ok(chatService.getAdvisors(pageable));
	}

}
