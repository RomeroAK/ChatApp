package org.chatapp.service;

import org.chatapp.model.ChatMessage;
import org.chatapp.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    // Save a chat message to the database
    public ChatMessage saveMessage(ChatMessage message) {
        return chatMessageRepository.save(message);
    }

    // Retrieve all chat messages from the database
    public List<ChatMessage> getAllMessages() {
        return chatMessageRepository.findAll();
    }
}
