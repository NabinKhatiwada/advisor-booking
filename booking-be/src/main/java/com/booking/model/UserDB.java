package com.booking.model;

import com.booking.enums.EUserRole;
import com.booking.enums.EUserStatus;
import com.booking.util.StringAttributeConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_user")
@Getter
@Setter
public class UserDB extends BaseDB {

	public UserDB() {
		this.emailVerified = false;
		this.status = EUserStatus.ACTIVE;
		this.role = EUserRole.ROLE_CLIENT;
	}

	private static final long serialVersionUID = 1L;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private EUserStatus status;

	@Column(name = "name")
    private String name;

	@Column(name = "email")
    private String email;

	@Column(name = "image_url")
    private String imageUrl;

	@Column(name = "email_verified")
	private Boolean emailVerified;

	@JsonIgnore
	@Convert(converter = StringAttributeConverter.class)
	@Column(name = "password")
	private String password;

	@Column(name = "provider")
	@Enumerated(EnumType.STRING)
    private AuthProvider provider;

	@Column(name = "provider_id")
    private String providerId;

	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private EUserRole role;
}
