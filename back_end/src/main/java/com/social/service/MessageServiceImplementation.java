package com.social.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.models.Chat;
import com.social.models.Message;
import com.social.models.User;
import com.social.repository.ChatRepository;
import com.social.repository.MessageRepository;

@Service
public class MessageServiceImplementation implements MessageService{
	
	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private ChatRepository chatRepository;
	
	@Autowired
	private ChatService chatService;

	@Override
	public Message createMessage(User user, Integer chatId, Message req) throws Exception {
		Message newMessage = new Message();
		Chat chat = chatService.findChatById(chatId);
		
		newMessage.setChat(chat);
		newMessage.setContent(req.getContent());
		newMessage.setImage(req.getImage());
		newMessage.setUser(user);
		newMessage.setTimeStamp(LocalDateTime.now());
		
		Message savedMessage = messageRepository.save(newMessage);
		chat.getMessages().add(savedMessage);
		chatRepository.save(chat);
		return savedMessage;
	}

	@Override
	public List<Message> findChatsMessages(Integer chatId) throws Exception {
		Chat chat = chatService.findChatById(chatId);
		return messageRepository.findByChatId(chatId);
	}

}
