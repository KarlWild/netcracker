package com.netcracker.auto.service.chat;

import com.netcracker.auto.entity.chat.ChatRoom;
import com.netcracker.auto.repository.chat.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    public Optional<Long> getChatId(
            Long senderId, Long recipientId, boolean createIfNotExist) {

        return chatRoomRepository
                .findBySenderIdAndRecipientId(senderId, recipientId)
                .map(ChatRoom::getChatId)
                .or(() -> {
                        if (!createIfNotExist) {
                            return Optional.empty();
                        }
                        ChatRoom senderRecipient = ChatRoom
                                .builder()
                                .senderId(senderId)
                                .recipientId(recipientId)
                                .build();
                        chatRoomRepository.save(senderRecipient);
                        return Optional.of(chatRoomRepository.findBySenderIdAndRecipientId(senderId,recipientId).get().getChatId());
                });
    }
//    public Optional<Long> getChatIdByAdId(Long adId, Long senderId, Long recipientId, boolean createIfNotExist){
//        return chatRoomRepository
//                .findByAdId(adId)
//                .map(ChatRoom::getChatId)
//                .or(() -> {
//                    if (!createIfNotExist) {
//                        return Optional.empty();
//                    }
//
//                    ChatRoom senderRecipient = ChatRoom
//                            .builder()
//                            .senderId(senderId)
//                            .recipientId(recipientId)
//                            .adId(adId)
//                            .build();
//
//
//                    chatRoomRepository.save(senderRecipient);
//
//                    return Optional.of(chatRoomRepository.findBySenderIdAndRecipientId(senderId,recipientId).get().getChatId());
//                });
//    }
}
