package com.netcracker.auto.entity.chat;

public class ChatNotification {
    private String id;
    private Long senderId;
    private String senderName;
    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getMessage(){
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public ChatNotification(String id, Long senderId, String senderName, String message) {
        this.id = id;
        this.senderId = senderId;
        this.senderName = senderName;
        this.message = message;
    }
}
