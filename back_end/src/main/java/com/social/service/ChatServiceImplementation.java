package com.social.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.models.Chat;
import com.social.models.User;
import com.social.repository.ChatRepository;

@Service
public class ChatServiceImplementation implements ChatService{
	
	@Autowired
	ChatRepository chatRepository;

	@Override
	public Chat createChat(User reqUser, User user2) {
		// TODO Auto-generated method stub
		Chat exist = chatRepository.findChatByUsersId(user2, reqUser);
		
		if(exist!= null) {
			return exist;
		}else {
			Chat chat = new Chat();
			chat.getUsers().add(user2);
			chat.getUsers().add(reqUser);
			chat.setTimeStamp(LocalDateTime.now());
			
			chatRepository.save(chat);
		}
		return null;
	}

	@Override
	public Chat findChatById(Integer chatId) throws Exception {
		Optional<Chat> chat = chatRepository.findById(chatId);
		
		if (chat.isEmpty()) {
			throw new Exception("chat not found with " + chatId);
		}
		return chat.get();
	}

	@Override
	public List<Chat> findUserChat(Integer userId) {
		
		
		return chatRepository.findByUsersId(userId);
	}

}
