package com.booking.model.dto;

import java.util.List;

import com.booking.enums.AuthProvider;
import com.booking.enums.EUserRole;
import com.booking.enums.EUserStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
	/* List of users only */
	public UserDTO(Long userId, EUserStatus status, String name, String imageUrl) {
		this.userId = userId;
		this.status = status;
		this.name = name;
		this.imageUrl = imageUrl;
	}

	/* List of users with roles */
	public UserDTO(Long userId, EUserStatus status, String name, String email, String imageUrl, EUserRole role) {
		this.userId = userId;
		this.status = status;
		this.name = name;
		this.email = email;
		this.imageUrl = imageUrl;
		this.role = role;
	}

	/* List of chat users */
	public UserDTO(Long userId, String name, String imageUrl) {
		this.userId = userId;
		this.name = name;
		this.imageUrl = imageUrl;
	}

	private Long userId;

	private EUserStatus status;

	private String name;

	private String imageUrl;

	private EUserRole role;

	private String email;

	/* Used for setting roles since 1 user can have Multiple roles */
	private List<EUserRole> roles;

	private AuthProvider provider;

	private TokenDTO tokenDTO;

}
