package com.example.websocketdemo.model;

/**
 * Created by rajeevkumarsingh on 24/07/17.
 */
public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;
    private String to;
    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
    public ChatMessage(String content,String sender,String to)
    {
    	this.content=content;
    	this.sender=sender;
    	this.to=to;
    	this.type=MessageType.CHAT;
    }
    public ChatMessage()
    {
    	
    }
    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

}
