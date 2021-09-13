package com.netcracker.auto.service.chat;

import com.netcracker.auto.entity.chat.ChatNotification;
import com.netcracker.auto.entity.chat.MessageStatus;
import com.netcracker.auto.repository.chat.ChatNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class ChatNotificationService {
    @Autowired
    ChatNotificationRepository chatNotificationRepository;
    public List<ChatNotification> findAllReceivedNotifications(){
        return chatNotificationRepository.findByMessageStatus(MessageStatus.RECEIVED);
    }
    public List<ChatNotification> findAllDeliveredNotifications(){
        return chatNotificationRepository.findByMessageStatus(MessageStatus.DELIVERED);
    }
    public List<ChatNotification> findAll(){
        List<ChatNotification> chatNotifications = chatNotificationRepository.findAll();
        Collections.sort(chatNotifications, (o1, o2) -> {
            boolean a = o1.getMessageStatus() == MessageStatus.DELIVERED;
            boolean b = o2.getMessageStatus() == MessageStatus.DELIVERED;
            return Boolean.compare(a, b);
        });
        return chatNotificationRepository.findAll();
    }
    public void saveNotification(ChatNotification chatNotification){
        chatNotificationRepository.save(chatNotification);
    }
}
/*
* Collections.sort(abc, new Comparator<Abc>() {
        @Override
        public int compare(Abc abc1, Abc abc2) {
            return Boolean.compare(abc2.isClickable,abc1.isClickable);
        }
    });*/
