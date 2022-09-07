package com.booking.model.dto;

import java.time.LocalDateTime;

public interface IChatMessage {
	Long getId();

	LocalDateTime getMessageDatetime();

	String getMessage();

	Long getSenderId();

}
