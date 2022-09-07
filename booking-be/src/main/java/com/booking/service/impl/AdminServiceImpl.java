package com.booking.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.booking.enums.EUserRole;
import com.booking.exception.BadRequestException;
import com.booking.exception.ResourceNotFoundException;
import com.booking.model.AuthProvider;
import com.booking.model.UserDB;
import com.booking.model.dto.TokenDTO;
import com.booking.model.dto.UserDTO;
import com.booking.repository.UserRepository;
import com.booking.security.TokenProvider;
import com.booking.service.AdminService;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenProvider tokenProvider;

	@Override
	public UserDTO loginByEmailAndPassword(String email, String password) {
		UserDB userDB = userRepository.findByEmailAndPassword(email, password)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		String token = tokenProvider.createToken(userDB.getId());
		TokenDTO tokenDTO = new TokenDTO();
		tokenDTO.setAccessToken(token);

		UserDTO userDTO = new UserDTO();
		userDTO.setEmail(userDB.getEmail());
		userDTO.setImageUrl(userDB.getImageUrl());
		userDTO.setName(userDB.getName());
		userDTO.setTokenDTO(tokenDTO);
		return userDTO;
	}

	@Override
	public void addAdvisor(UserDTO userDTO) {
		if(!StringUtils.hasText(userDTO.getEmail())) {
			throw new BadRequestException("Email is mandatory");
		}
		
		UserDB userDB = userRepository.findByEmail(userDTO.getEmail()).orElse(null);
		if (userDB == null) {
			userDB = new UserDB();
		}
		userDB.setEmail(userDTO.getEmail());
		userDB.setRole(EUserRole.ROLE_ADVISOR);
		userDB.setProvider(AuthProvider.google);

		userRepository.save(userDB);
	}

}
