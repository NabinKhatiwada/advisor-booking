package com.booking.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.booking.enums.EAppointmentStatus;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_appointment")
@Getter
@Setter
public class AppointmentDB extends BaseDB {
	private static final long serialVersionUID = 1L;

	public AppointmentDB() {
		this.status = EAppointmentStatus.AVAILABLE;
	}

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private EAppointmentStatus status;

	@Column(name = "appointment_datetime")
	private LocalDateTime appointmentDatetime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "advisor_id", referencedColumnName = "id")
	private UserDB advisor;

	@Column(name = "advisor_id", insertable = false, updatable = false)
	private Long advisorId;

}
