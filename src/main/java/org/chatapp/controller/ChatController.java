package org.chatapp.controller;

import org.chatapp.model.ChatMessage;
import org.chatapp.model.User;
import org.chatapp.repository.UserRepository;
import org.chatapp.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private UserRepository userRepository;

    @Autowired
    private ChatMessageService chatMessageService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/send")
    public ResponseEntity<ChatMessage> sendMessage(@RequestBody ChatMessage message) {
        Optional<User> receiver = userRepository.findByUsername(message.getRecipient());
        if (receiver.isPresent()) {
            ChatMessage savedMessage = chatMessageService.saveMessage(message);
            return ResponseEntity.ok(savedMessage);
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/messages")
    public ResponseEntity<List<ChatMessage>> getMessages() {
        List<ChatMessage> messages = chatMessageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }


}
