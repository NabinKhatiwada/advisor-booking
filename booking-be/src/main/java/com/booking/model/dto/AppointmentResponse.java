package com.booking.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class AppointmentResponse {

	private List<AppointmentDTO> appointments;

	private PaginationInfo paginationInfo;

}
