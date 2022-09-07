package com.booking.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.booking.enums.EUserRole;
import com.booking.exception.BadRequestException;
import com.booking.model.ChatDB;
import com.booking.model.UserDB;
import com.booking.model.dto.ChatDTO;
import com.booking.model.dto.ChatResponse;
import com.booking.model.dto.IChat;
import com.booking.model.dto.IChatMessage;
import com.booking.model.dto.UserDTO;
import com.booking.model.dto.UserResponse;
import com.booking.repository.ChatRespository;
import com.booking.service.ChatService;
import com.booking.service.UserService;
import com.booking.util.CommonMethods;
import com.booking.util.DateUtils;

@Service
@Transactional
public class ChatServiceImpl implements ChatService {

	@Autowired
	private UserService userService;

	@Autowired
	private ChatRespository chatRespository;

	@Override
	public void sendMessage(ChatDTO request) {
		if (!StringUtils.hasText(request.getMessage())) {
			throw new BadRequestException("Message is mandatory");
		}
		if (request.getReceiverId() == null || request.getReceiverId() <= 0) {
			throw new BadRequestException("Receiver is mandatory");
		}
		UserDB sender = userService.getUser(request.getSenderId());
		UserDB receiver = userService.getUser(request.getReceiverId());
		ChatDB chatDB = new ChatDB();
		chatDB.setReceiver(receiver);
		chatDB.setSender(sender);
		chatDB.setMessage(request.getMessage());
		chatDB.setMessageDatetime(LocalDateTime.now());
		chatRespository.save(chatDB);
	}

	@Override
	public ChatResponse getMessages(Long userId, Long nextUserId, Pageable pageable) {
		Page<IChatMessage> page = chatRespository.getMessages(userId, nextUserId, pageable);

		List<IChatMessage> ichats = page.getContent();
		List<ChatDTO> chatDTOList = new ArrayList<>();
		for (IChatMessage chat : ichats) {
			Long chatId = chat.getId();
			LocalDateTime datetime = chat.getMessageDatetime();
			String message = chat.getMessage();
			Long senderId = chat.getSenderId();

			ChatDTO chatDTO = new ChatDTO();
			chatDTO.setChatId(chatId);
			chatDTO.setMessageDatetimeStr(DateUtils.convertDateTimeToStr(datetime));
			chatDTO.setMessage(message);
			chatDTO.setSenderId(senderId);

			chatDTOList.add(chatDTO);
		}
		ChatResponse chatResponse = new ChatResponse();
		chatResponse.setChats(chatDTOList);
		chatResponse.setPagInfo(CommonMethods.getPaginationInfo(page));
		return chatResponse;
	}

	@Override
	public ChatResponse getChatHeads(Long userId, Pageable pageable) {
		Page<IChat> page = chatRespository.getChatHeads(userId, pageable);
		List<IChat> ichats = page.getContent();
		List<ChatDTO> chatDTOList = new ArrayList<>();
		for (IChat chat : ichats) {
			Long nextUserId = chat.getId();
			String name = chat.getName();

			ChatDTO chatDTO = new ChatDTO();
			chatDTO.setNextuserId(nextUserId);
			chatDTO.setNextuserName(name);
			chatDTOList.add(chatDTO);
		}
		ChatResponse chatResponse = new ChatResponse();
		chatResponse.setChats(chatDTOList);
		chatResponse.setPagInfo(CommonMethods.getPaginationInfo(page));
		return chatResponse;
	}

	@Override
	public UserResponse getClients(Pageable pageable) {
		Page<UserDTO> page = userService.getUsers(EUserRole.ROLE_CLIENT, pageable);
		UserResponse response = new UserResponse();
		response.setPaginationInfo(CommonMethods.getPaginationInfo(page));
		response.setUsers(page.getContent());
		return response;
	}

	@Override
	public UserResponse getAdvisors(Pageable pageable) {
		Page<UserDTO> page = userService.getUsers(EUserRole.ROLE_ADVISOR, pageable);
		UserResponse response = new UserResponse();
		response.setPaginationInfo(CommonMethods.getPaginationInfo(page));
		response.setUsers(page.getContent());
		return response;
	}

}
