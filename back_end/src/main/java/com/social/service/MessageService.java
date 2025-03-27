package com.social.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.social.models.Chat;
import com.social.models.Message;
import com.social.models.User;
import com.social.repository.MessageRepository;

public interface MessageService {
	
	public Message createMessage(User user, Integer chatId, Message req) throws Exception;
	
	public List<Message> findChatsMessages(Integer chatId) throws Exception;
}
