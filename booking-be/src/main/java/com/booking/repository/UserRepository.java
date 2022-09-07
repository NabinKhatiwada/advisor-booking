package com.booking.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.booking.enums.EUserRole;
import com.booking.model.UserDB;
import com.booking.model.dto.UserDTO;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDB, Long> {
	Optional<UserDB> findByEmail(String email);

	Optional<UserDB> findByEmailAndPassword(String email, String password);

	@Query("SELECT NEW com.booking.model.dto.UserDTO(u.id,u.name,u.imageUrl) FROM UserDB u WHERE u.role = :role ")
	Page<UserDTO> getUsers(EUserRole role, Pageable pageable);
}
