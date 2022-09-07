package com.booking.service;

import org.springframework.data.domain.Pageable;

import com.booking.model.dto.AppointmentDTO;
import com.booking.model.dto.AppointmentResponse;

public interface AppointmentService {

	void createAppointment(AppointmentDTO request);

	void bookAppointment(Long userId, Long appointmentId);

	void cancelAppointment(Long userId, Long userAppointmentId);

	AppointmentResponse getUpcomingAppointments(Long userId, Pageable pageable);

	AppointmentResponse getUpcomingAppointmentsForClient(Pageable pageable);

	AppointmentResponse getMyBookings(Long userId, Pageable pageable);

}
