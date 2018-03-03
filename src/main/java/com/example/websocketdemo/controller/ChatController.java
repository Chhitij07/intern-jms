package com.example.websocketdemo.controller;

import com.example.websocketdemo.model.ChatMessage;
import com.example.websocketdemo.model.UserDetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.stereotype.Controller;

/**
 * Created by rajeevkumarsingh on 24/07/17.
 */
@Controller
public class ChatController {

	
	private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

	UserDetails user=new UserDetails();
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
    	//logger.info(chatMessage.getTo()+" This is here ");
    	String send_to=chatMessage.getTo();
    	
    	messagingTemplate.convertAndSendToUser(user.get(send_to),"/queue/reply", chatMessage,createHeaders(user.get(send_to)));//logger.info(headerAccessor.getSessionId());
    	//logger.info(user.get(send_to)+" "+headerAccessor.getMessageHeaders());
    	//user.show();
  
    }
    
    
    private MessageHeaders createHeaders(String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }
    
    
    
    

    @MessageMapping("/chat.addUser")
    public void addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        messagingTemplate.convertAndSendToUser(headerAccessor.getSessionId(),"/queue/reply", chatMessage,headerAccessor.getMessageHeaders());
        logger.info(headerAccessor.getSessionId());
        user.add(chatMessage.getSender(),headerAccessor.getSessionId());
        user.show();
        
        
        
    }

}
