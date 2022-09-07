package com.booking.model.dto;

import java.time.LocalDateTime;

import com.booking.util.DateUtils;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class ChatDTO {
	/* For chat heads */
	public ChatDTO(Long chatId, Long nextuserId, String nextuserName) {
		this.chatId = chatId;
		this.nextuserId = nextuserId;
		this.nextuserName = nextuserName;
	}

	/* For chats */
	public ChatDTO(Long chatId, Long senderId, String senderName, Long receiverId, String receiverName,
			LocalDateTime messageDatetime, String message) {
		this.chatId = chatId;
		this.senderId = senderId;
		this.senderName = senderName;
		this.receiverId = receiverId;
		this.receiverName = receiverName;
		this.messageDatetimeStr = DateUtils.convertDateTimeToStr(messageDatetime);
		this.message = message;
	}

	private Long chatId;
	private Long senderId;
	private String senderName;
	private Long receiverId;
	private String receiverName;
	private String messageDatetimeStr;
	private String message;
	private String name;
	private String imageUrl;
	private Long nextuserId;
	private String nextuserName;
}
