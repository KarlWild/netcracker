package com.netcracker.auto.repository.chat;

import com.netcracker.auto.entity.chat.ChatRoom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChatRoomRepository extends CrudRepository<ChatRoom, Long> {
    @Query("select c from ChatRoom c where (c.recipientId = :recipientId and c.senderId = :senderId) or (c.recipientId = :senderId and c.senderId = :recipientId) ")
    Optional<ChatRoom> findBySenderIdAndRecipientId(@Param("senderId") Long senderId,@Param("recipientId") Long recipientId);

    //Optional<ChatRoom> findByAdId(Long adId);
}
