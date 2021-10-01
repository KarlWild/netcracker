package com.netcracker.auto.repository.chat;

import com.netcracker.auto.entity.chat.ChatMessage;
import com.netcracker.auto.entity.chat.MessageStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ChatMessageRepository
        extends CrudRepository<ChatMessage, Long> {

    long countBySenderIdAndRecipientIdAndStatus(
            Long senderId, Long recipientId, MessageStatus status);

    List<ChatMessage> findByChatId(Long chatId);

    @Modifying
    @Transactional
    @Query("update ChatMessage c set c.status = :status where (c.senderId = :senderId and c.recipientId = :recipientId) or (c.recipientId = :senderId and c.senderId = :recipientId)")
    void updateStatuses(@Param("senderId") Long senderId, @Param("recipientId") Long recipientId,@Param("status") MessageStatus status);
}