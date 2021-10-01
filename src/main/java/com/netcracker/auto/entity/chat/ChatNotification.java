package com.netcracker.auto.entity.chat;

import javax.persistence.*;

@Entity
@Table(name = "chatnotification")
public class ChatNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "notification_id")
    private Long notificationId;
    @Column(name = "id")
    private String id;
    @Column(name = "senderId")
    private Long senderId;
    @Column(name = "senderName")
    private String senderName;
    @Column(name = "message")
    private String message;
    @Column
    private MessageStatus messageStatus;

    {
        messageStatus = MessageStatus.RECEIVED;
    }

    public ChatNotification(){}
    public ChatNotification(String id, Long senderId, String senderName, String message) {
        this.id = id;
        this.senderId = senderId;
        this.senderName = senderName;
        this.message = message;
    }
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

    public MessageStatus getMessageStatus() {
        return messageStatus;
    }
}
