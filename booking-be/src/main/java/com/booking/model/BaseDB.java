package com.booking.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseDB implements Serializable{

	private static final long serialVersionUID = 1L;

	protected BaseDB() {
		this.createdDateTime = this.updatedDateTime = LocalDateTime.now();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@Column(name = "created_datetime")
	protected LocalDateTime createdDateTime;

	@Column(name = "updated_datetime")
	protected LocalDateTime updatedDateTime;
}
