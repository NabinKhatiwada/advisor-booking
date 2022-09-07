package com.booking.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserResponse {
	private List<UserDTO> users;

	private PaginationInfo paginationInfo;
}
