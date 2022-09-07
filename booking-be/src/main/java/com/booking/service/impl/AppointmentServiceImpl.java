package com.booking.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.booking.enums.EAppointmentStatus;
import com.booking.enums.EUserAppointmentStatus;
import com.booking.exception.ResourceNotFoundException;
import com.booking.model.AppointmentDB;
import com.booking.model.UserAppointmentDB;
import com.booking.model.UserDB;
import com.booking.model.dto.AppointmentDTO;
import com.booking.model.dto.AppointmentResponse;
import com.booking.model.dto.PaginationInfo;
import com.booking.repository.AppointmentRepository;
import com.booking.repository.UserAppointmentRespository;
import com.booking.service.AppointmentService;
import com.booking.service.UserService;
import com.booking.util.CommonMethods;
import com.booking.util.DateUtils;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private UserService userService;

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private UserAppointmentRespository userAppointmentRespository;

	@Override
	public void createAppointment(AppointmentDTO request) {
		UserDB userDB = userService.getUser(request.getUserId());

		LocalDateTime appointmentDateTime = DateUtils.convertStrToDateTime(request.getAppointmentDatetimeStr());
		
		AppointmentDB appointmentDB = new AppointmentDB();
		appointmentDB.setAdvisor(userDB);
		appointmentDB.setAppointmentDatetime(appointmentDateTime);

		appointmentRepository.save(appointmentDB);
	}

	@Override
	public void bookAppointment(Long userId, Long appointmentId) {
		AppointmentDB appointmentDB = appointmentRepository
				.findByIdAndStatus(appointmentId, EAppointmentStatus.AVAILABLE)
				.orElseThrow(() -> new ResourceNotFoundException("appointment not found"));
		// Changing appointment to booked status so it cannot be booked until it is
		// available
		appointmentDB.setStatus(EAppointmentStatus.BOOKED);
		appointmentRepository.save(appointmentDB);

		UserDB userDB = userService.getUser(userId);

		UserAppointmentDB userAppointmentDB = new UserAppointmentDB();
		userAppointmentDB.setAppointment(appointmentDB);
		userAppointmentDB.setUser(userDB);
		userAppointmentDB.setStatus(EUserAppointmentStatus.CONFIRMED);
		userAppointmentRespository.save(userAppointmentDB);
	}

	@Override
	public void cancelAppointment(Long userId, Long userAppointmentId) {
		// Making sure that given appointmentId is user's own appointment by passing
		// 'userId' & userAppointmentId in query
		UserAppointmentDB userAppointmentDB = userAppointmentRespository
				.findByIdAndUserIdAndStatus(userAppointmentId, userId, EUserAppointmentStatus.CONFIRMED)
				.orElseThrow(() -> new ResourceNotFoundException("appointment not found"));
		userAppointmentDB.setStatus(EUserAppointmentStatus.CANCELLED);
		userAppointmentRespository.save(userAppointmentDB);

		// Once cancelled, appointment is available to book again
		AppointmentDB appointmentDB = userAppointmentDB.getAppointment();
		appointmentDB.setStatus(EAppointmentStatus.AVAILABLE);

		appointmentRepository.save(appointmentDB);
	}

	@Override
	public AppointmentResponse getUpcomingAppointments(Long userId, Pageable pageable) {
		LocalDateTime now = LocalDateTime.now();
		Page<AppointmentDTO> page = appointmentRepository.getUpcomingAppointments(userId, now,
				EUserAppointmentStatus.CONFIRMED, pageable);
		List<AppointmentDTO> appointments = page.getContent();
		PaginationInfo info = CommonMethods.getPaginationInfo(page);

		AppointmentResponse response = new AppointmentResponse();
		response.setPaginationInfo(info);
		response.setAppointments(appointments);
		return response;
	}

	@Override
	public AppointmentResponse getUpcomingAppointmentsForClient(Pageable pageable) {
		LocalDateTime now = LocalDateTime.now();
		Page<AppointmentDTO> page = appointmentRepository.getUpcomingAppointmentsForClient(now, pageable);
		List<AppointmentDTO> appointments = page.getContent();
		PaginationInfo info = CommonMethods.getPaginationInfo(page);

		AppointmentResponse response = new AppointmentResponse();
		response.setPaginationInfo(info);
		response.setAppointments(appointments);
		return response;
	}

	@Override
	public AppointmentResponse getMyBookings(Long userId, Pageable pageable) {
		Page<AppointmentDTO> page = userAppointmentRespository.getMyBookings(userId, pageable);
		List<AppointmentDTO> appointments = page.getContent();
		PaginationInfo info = CommonMethods.getPaginationInfo(page);

		AppointmentResponse response = new AppointmentResponse();
		response.setPaginationInfo(info);
		response.setAppointments(appointments);
		return response;
	}

}
