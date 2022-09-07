package com.booking.model.dto;

import java.time.LocalDateTime;

import com.booking.enums.EAppointmentStatus;
import com.booking.enums.EUserAppointmentStatus;
import com.booking.util.CommonMethods;
import com.booking.util.DateUtils;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppointmentDTO {

	/* List for upcoming appointment for advisors & client both */
	public AppointmentDTO(Long appointmentId, EAppointmentStatus status, LocalDateTime datetime, String name,
			String email) {
		this.appointmentId = appointmentId;
		this.appointmentStatus = status;
		this.appointmentDatetimeStr = DateUtils.convertDateTimeToStr(datetime);
		this.name = name;
		this.email = email;
	}

//	/* List for clients */
//	public AppointmentDTO(Long appointmentId, EAppointmentStatus status, LocalDateTime datetime, String advisorName,String email) {
//		this.appointmentId = appointmentId;
//		this.appointmentStatus = status;
//		this.appointmentDatetimeStr = DateUtils.convertDateTimeToStr(datetime);
//		this.name = name;
//	}

	/* List for client's booked appointments */
	public AppointmentDTO(Long userAppointmentId, LocalDateTime datetime,
			EUserAppointmentStatus userAppointmentStatus, String name, String email) {
		this.userAppointmentId = userAppointmentId;
		this.appointmentDatetimeStr = DateUtils.convertDateTimeToStr(datetime);
		this.userAppointmentStatus = userAppointmentStatus;
		this.name = name;
		this.email = email;
	}

	private Long appointmentId;

	private Long userAppointmentId;

	private EAppointmentStatus appointmentStatus;

	private EUserAppointmentStatus userAppointmentStatus;

	private String appointmentDatetimeStr;

	private Long userId;

	private String name;

	private String advisorName;

	private String clientName;

	private String email;
}
