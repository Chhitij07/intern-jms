package com.example.websocketdemo.controller;

import com.example.websocketdemo.model.ChatMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

/**
 * Created by rajeevkumarsingh on 24/07/17.
 */
@Controller
public class ChatController {

	

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage chatMessage) {

        messagingTemplate.convertAndSend("/queue/"+chatMessage.getSender(), chatMessage);
        
    }

    @MessageMapping("/chat.addUser")
    public void addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        messagingTemplate.convertAndSend("/queue/"+chatMessage.getSender(), chatMessage);
    }

}
