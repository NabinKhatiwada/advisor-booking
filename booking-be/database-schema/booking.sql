create table tbl_user(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	created_datetime DATETIME NOT NULL,
	updated_datetime DATETIME NOT NULL,
    status ENUM('ACTIVE','INACTIVE')  NOT NULL,
    name VARCHAR(150),
    email VARCHAR(150) NOT NULL,
    image_url VARCHAR(256),
    email_verified BIT(1),
    provider VARCHAR(50),
    provider_id VARCHAR(256),
    password VARCHAR(256),
    role ENUM('ROLE_ADMIN', 'ROLE_ADVISOR', 'ROLE_CLIENT') NOT NULL
);

create table tbl_appointment(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
	created_datetime DATETIME NOT NULL,
	updated_datetime DATETIME NOT NULL,
    status ENUM('AVAILABLE', 'BOOKED','COMPLETED')  NOT NULL,
    advisor_id BIGINT NOT NULL,
    appointment_datetime DATETIME NOT NULL,
	CONSTRAINT FK_appointment_advisor_id FOREIGN KEY(advisor_id) REFERENCES tbl_user(id)
);

-- Mapping of appointment & user booked appointment. A person cannot book same appointment twice
create table tbl_user_appointment(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
	created_datetime DATETIME NOT NULL,
	updated_datetime DATETIME NOT NULL,
    status ENUM('CONFIRMED', 'CANCELLED', 'COMPLETED')  NOT NULL,
    appointment_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
	CONSTRAINT FK_user_appointment_userId FOREIGN KEY(user_id) REFERENCES tbl_user(id),
	CONSTRAINT FK_user_appointment_appointmentId FOREIGN KEY(appointment_id) REFERENCES tbl_appointment(id),
	CONSTRAINT UQ_userId_appointmentId UNIQUE KEY(user_id,appointment_id)
);

create table tbl_chat(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
 	sender_id BIGINT NOT NULL,
  	receiver_id BIGINT NOT NULL,
  	message_datetime DATETIME NOT NULL,
    message varchar(1000) NOT NULL,
    constraint FK_chat_sender_id foreign key(sender_id) references tbl_user(id),
    constraint FK_chat_receiver_id foreign key(receiver_id) references tbl_user(id)	
);

-- admin to be manually inserted once in db -> password is Password@123
insert into tbl_user(created_datetime,updated_datetime,status,name,email,password,role)values('2022-8-21','2022-08-21','ACTIVE','Admin','admin@gmail.com','uESlvS9IGDFTYB09f2IN6Q==','ROLE_ADMIN');