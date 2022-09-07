package com.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.booking.model.dto.LoginDTO;
import com.booking.model.dto.UserDTO;
import com.booking.service.AdminService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@PostMapping("login")
	@ResponseBody
	@PreAuthorize("permitAll()")
	@Operation(summary = "admin-login", operationId = "", description = " ")
	public ResponseEntity<?> login(@RequestBody LoginDTO login) {
		return ResponseEntity.ok(adminService.loginByEmailAndPassword(login.getEmail(), login.getPassword()));
	}

	@PostMapping("advisor")
	@ResponseBody
	@PreAuthorize("hasAnyRole('ADMIN')")
	@Operation(summary = "add-advisor", operationId = "", description = "mandatory field: email")
	public ResponseEntity<?> addAdvisor(@RequestBody UserDTO userDTO) {
		adminService.addAdvisor(userDTO);
		return ResponseEntity.ok(null);
	}
}
