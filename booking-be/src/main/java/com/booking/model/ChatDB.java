package com.booking.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_chat")
@Getter
@Setter
public class ChatDB implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_id", referencedColumnName = "id")
	private UserDB sender;

	@Column(name = "sender_id", insertable = false, updatable = false)
	private Long senderId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiver_id", referencedColumnName = "id")
	private UserDB receiver;

	@Column(name = "receiver_id", insertable = false, updatable = false)
	private Long receiverId;

	@Column(name = "message")
	private String message;

	@Column(name = "message_datetime")
	private LocalDateTime messageDatetime;

}
