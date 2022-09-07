package com.booking.service;

import org.springframework.data.domain.Pageable;

import com.booking.model.dto.ChatDTO;
import com.booking.model.dto.ChatResponse;
import com.booking.model.dto.UserResponse;

public interface ChatService {

	void sendMessage(ChatDTO request);

	ChatResponse getMessages(Long userId, Long nextUserId, Pageable pageable);

	ChatResponse getChatHeads(Long userId, Pageable pageable);

	UserResponse getClients(Pageable pageable);

	UserResponse getAdvisors(Pageable pageable);

}
