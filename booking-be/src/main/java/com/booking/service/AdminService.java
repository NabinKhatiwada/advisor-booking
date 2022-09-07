package com.booking.service;

import com.booking.model.dto.UserDTO;

public interface AdminService {

	UserDTO loginByEmailAndPassword(String email, String password);

	void addAdvisor(UserDTO userDTO);

}
