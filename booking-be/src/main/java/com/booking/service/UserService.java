package com.booking.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.booking.enums.EUserRole;
import com.booking.model.UserDB;
import com.booking.model.dto.UserDTO;

public interface UserService {
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

	UserDetails loadUserById(Long id);

	UserDB getUser(Long userId);

	Page<UserDTO> getUsers(EUserRole role, Pageable pageable);
}
