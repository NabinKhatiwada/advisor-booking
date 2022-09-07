package com.booking.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.booking.enums.EAppointmentStatus;
import com.booking.enums.EUserAppointmentStatus;
import com.booking.model.AppointmentDB;
import com.booking.model.dto.AppointmentDTO;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentDB, Long> {

	Optional<AppointmentDB> findByIdAndStatus(Long appointmentId, EAppointmentStatus available);

	/*
	 * Only upcoming,not past. Also user details if user has booked any of the
	 * appointment
	 */
	@Query("SELECT new com.booking.model.dto.AppointmentDTO(a.id,a.status,a.appointmentDatetime,u.name,u.email) FROM AppointmentDB a "
			+ "LEFT JOIN UserAppointmentDB ua ON ua.appointmentId = a.id AND ua.status = :usrAppointStatus "
			+ "LEFT JOIN UserDB u ON ua.userId = u.id "
			+ "WHERE a.advisorId = :advisorId AND a.appointmentDatetime >= :date ORDER BY a.appointmentDatetime ASC  ")
	Page<AppointmentDTO> getUpcomingAppointments(Long advisorId, LocalDateTime date,
			EUserAppointmentStatus usrAppointStatus, Pageable pageable);

	@Query("SELECT new com.booking.model.dto.AppointmentDTO(a.id,a.status,a.appointmentDatetime,u.name,u.email) FROM AppointmentDB a "
			+ "LEFT JOIN UserDB u ON a.advisorId = u.id  WHERE a.appointmentDatetime >= :date ORDER BY a.appointmentDatetime ASC ")
	Page<AppointmentDTO> getUpcomingAppointmentsForClient(LocalDateTime date, Pageable pageable);

}
