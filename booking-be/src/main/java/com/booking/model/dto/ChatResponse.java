package com.booking.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class ChatResponse {
	List<ChatDTO> chats;
	PaginationInfo pagInfo;
}
