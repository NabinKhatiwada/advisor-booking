package com.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.booking.exception.ResourceNotFoundException;
import com.booking.model.UserDB;
import com.booking.model.dto.AppointmentDTO;
import com.booking.repository.UserRepository;
import com.booking.security.CurrentUser;
import com.booking.security.UserPrincipal;
import com.booking.service.AppointmentService;
import com.booking.service.UserService;
import com.booking.util.CommonMethods;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private AppointmentService appointmentService;

    @GetMapping("/profile")
	@PreAuthorize("hasAnyRole('CLIENT','ADVISOR')")
	public UserDB getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
    
	@PostMapping("appointment")
	@ResponseBody
	@PreAuthorize("hasAnyRole('ADVISOR')")
	@Operation(summary = "create appointment - ROLE ADVISOR", operationId = "", description = "appointmentDateTimeStr* ")
	public ResponseEntity<?> createAppointment(@RequestBody AppointmentDTO request) {
		Long userId = CommonMethods.getUserPrincipal().getId();
		request.setUserId(userId);
		appointmentService.createAppointment(request);
		return ResponseEntity.ok(null);
	}

	@GetMapping("appointment")
	@ResponseBody
	@PreAuthorize("hasAnyRole('ADVISOR')")
	@Operation(summary = "get upcoming appointments  - ROLE ADVISOR", operationId = "", description = "appointmentDateTimeStr* ")
	public ResponseEntity<?> getUpcomingAppointments(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "50") Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Long userId = CommonMethods.getUserPrincipal().getId();
		return ResponseEntity.ok(appointmentService.getUpcomingAppointments(userId, pageable));
	}

	@GetMapping("appointmentClient")
	@ResponseBody
	@PreAuthorize("hasAnyRole('CLIENT')")
	@Operation(summary = "get upcoming appointments  - ROLE CLIENT", operationId = "", description = "appointmentDateTimeStr* ")
	public ResponseEntity<?> getUpcomingAppointmentsForClient(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "50") Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		return ResponseEntity.ok(appointmentService.getUpcomingAppointmentsForClient(pageable));
	}

	@PostMapping("bookAppointment/{appointmentId}")
	@ResponseBody
	@PreAuthorize("hasRole('CLIENT')")
	@Operation(summary = "book appointment  - ROLE CLIENT", operationId = "", description = "appointmentId* ")
	public ResponseEntity<?> bookAppointment(@PathVariable("appointmentId") Long appointmentId) {
		Long userId = CommonMethods.getUserPrincipal().getId();
		appointmentService.bookAppointment(userId, appointmentId);
		return ResponseEntity.ok(null);
	}

	@GetMapping("getMyBookings")
	@ResponseBody
	@PreAuthorize("hasAnyRole('CLIENT')")
	@Operation(summary = "get client's booked appointments  - ROLE CLIENT", operationId = "", description = "appointmentDateTimeStr* ")
	public ResponseEntity<?> getMyBookings(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "50") Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Long userId = CommonMethods.getUserPrincipal().getId();
		return ResponseEntity.ok(appointmentService.getMyBookings(userId, pageable));
	}

	@DeleteMapping("deleteAppointment/{userAppointmentId}")
	@ResponseBody
	@PreAuthorize("hasRole('CLIENT')")
	@Operation(summary = "cancel appointment  - ROLE CLIENT", operationId = "", description = "userAppointmentId* ")
	public ResponseEntity<?> cancelAppointment(@PathVariable("userAppointmentId") Long userAppointmentId) {
		Long userId = CommonMethods.getUserPrincipal().getId();
		appointmentService.cancelAppointment(userId, userAppointmentId);
		return ResponseEntity.ok(null);
	}
    
    
}
