package com.booking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.booking.enums.EUserAppointmentStatus;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_user_appointment")
@Setter
@Getter
/* For Client Appointment data */
public class UserAppointmentDB extends BaseDB {

	private static final long serialVersionUID = 1L;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private EUserAppointmentStatus status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "appointment_id", referencedColumnName = "id")
	private AppointmentDB appointment;

	@Column(name = "appointment_id", insertable = false, updatable = false)
	private Long appointmentId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private UserDB user;

	@Column(name = "user_id", insertable = false, updatable = false)
	private Long userId;

}
