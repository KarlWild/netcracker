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
                    //if(chatRoomRepository.findByRecipientIdAndSenderId(senderId, recipientId).get()==null) {
                        if (!createIfNotExist) {
                            return Optional.empty();
                        }
//                        String chatId =
//                                String.format("%s_%s", senderId, recipientId);

                        ChatRoom senderRecipient = ChatRoom
                                .builder()
                                //.chatId(chatId)
                                .senderId(senderId)
                                .recipientId(recipientId)
                                .build();

//                        ChatRoom recipientSender = ChatRoom
//                                .builder()
//                                //.chatId(chatId)
//                                .senderId(recipientId)
//                                .recipientId(senderId)
//                                .build();
                        chatRoomRepository.save(senderRecipient);
                        //chatRoomRepository.save(recipientSender);

                        return Optional.of(chatRoomRepository.findBySenderIdAndRecipientId(senderId,recipientId).get().getChatId());
                    //}
                    //else {
                    //    return Optional.of(chatRoomRepository.findByRecipientIdAndSenderId(senderId, recipientId).get().getChatId());
                    //}
                });
    }
}
