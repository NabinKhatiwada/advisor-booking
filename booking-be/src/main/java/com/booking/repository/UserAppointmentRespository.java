package com.booking.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.booking.enums.EUserAppointmentStatus;
import com.booking.model.UserAppointmentDB;
import com.booking.model.dto.AppointmentDTO;

@Repository
public interface UserAppointmentRespository extends JpaRepository<UserAppointmentDB, Long> {

	Optional<UserAppointmentDB> findByIdAndUserIdAndStatus(Long userAppointmentId, Long userId,
			EUserAppointmentStatus status);

	@Query("SELECT new com.booking.model.dto.AppointmentDTO(ua.id,a.appointmentDatetime,ua.status,u.name,u.email) FROM AppointmentDB a "
			+ "INNER JOIN UserAppointmentDB ua ON ua.appointmentId = a.id AND ua.userId = :userId "
			+ "INNER JOIN UserDB u on u.id = a.advisorId ORDER BY a.appointmentDatetime ASC ")
	Page<AppointmentDTO> getMyBookings(Long userId, Pageable pageable);

}
