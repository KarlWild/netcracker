package com.netcracker.auto.repository.chat;

import com.netcracker.auto.entity.chat.ChatNotification;
import com.netcracker.auto.entity.chat.MessageStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatNotificationRepository extends CrudRepository<ChatNotification, Long> {
    List<ChatNotification> findAll();
    //@Query("select t from ChatNotification t where t.messageStatus=:")
    List<ChatNotification> findByMessageStatus(MessageStatus messageStatus);

}
