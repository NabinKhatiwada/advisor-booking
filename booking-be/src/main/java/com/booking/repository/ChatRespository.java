package com.booking.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.booking.model.ChatDB;
import com.booking.model.dto.IChat;
import com.booking.model.dto.IChatMessage;

@Repository
public interface ChatRespository extends JpaRepository<ChatDB, Long> {

	@Query(value = "SELECT u.id,u.name as name FROM tbl_chat c "
			+ "JOIN tbl_user u ON c.receiver_id = u.id WHERE c.sender_id = :userId GROUP BY u.id "
			+ "UNION SELECT u.id,u.name as name FROM tbl_chat c JOIN tbl_user u ON c.sender_id = u.id "
			+ "WHERE c.receiver_id = :userId GROUP BY u.id ", nativeQuery = true)
	Page<IChat> getChatHeads(Long userId, Pageable pageable);

	@Query(value = "SELECT * FROM (select c.id,c.message_datetime as messageDatetime,c.sender_id as senderId,c.message from tbl_chat c "
			+ "WHERE c.sender_id=:senderId AND c.receiver_id=:receiverId "
			+ "UNION select c.id,c.message_datetime as messageDatetime,c.sender_id as senderId,c.message from tbl_chat c "
			+ "WHERE c.sender_id=:receiverId AND c.receiver_id=:senderId ) "
			+ "as chats order by chats.messageDatetime asc ", nativeQuery = true)
	Page<IChatMessage> getMessages(Long senderId, Long receiverId, Pageable pageable);

}
