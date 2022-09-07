package com.booking.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.booking.enums.EUserRole;
import com.booking.exception.ResourceNotFoundException;
import com.booking.model.UserDB;
import com.booking.model.dto.UserDTO;
import com.booking.repository.UserRepository;
import com.booking.security.UserPrincipal;
import com.booking.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
	public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
		UserDB user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email : " + email)
                );

        return UserPrincipal.create(user);
    }

    @Override
	public UserDetails loadUserById(Long id) {
		UserDB user = userRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("User not found")
        );

        return UserPrincipal.create(user);
    }

	@Override
	public UserDB getUser(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
	}

	@Override
	public Page<UserDTO> getUsers(EUserRole role, Pageable pageable) {
		return userRepository.getUsers(role, pageable);
	}
}
