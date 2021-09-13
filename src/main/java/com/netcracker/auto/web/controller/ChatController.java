package com.netcracker.auto.web.controller;

import com.netcracker.auto.entity.User;
import com.netcracker.auto.entity.chat.ChatMessage;
import com.netcracker.auto.entity.chat.ChatNotification;
import com.netcracker.auto.repository.UserRepository;
import com.netcracker.auto.service.UserService;
import com.netcracker.auto.service.chat.ChatMessageService;
import com.netcracker.auto.service.chat.ChatNotificationService;
import com.netcracker.auto.service.chat.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ChatController {

    @Autowired private SimpMessagingTemplate messagingTemplate;
    @Autowired private UserRepository userRepository;
    @Autowired private ChatMessageService chatMessageService;
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    UserService userService;
    @Autowired private ChatNotificationService chatNotificationService;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {
        var chatId = chatRoomService
                .getChatId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true);
        chatMessage.setChatId(chatId.get());

        ChatMessage saved = chatMessageService.save(chatMessage);
        ChatNotification notification = new ChatNotification(saved.getId(), saved.getSenderId(), saved.getSenderName(), saved.getContent());
        chatNotificationService.saveNotification(notification);
        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId().toString(),"/queue/messages", notification);
    }
    @GetMapping("/chat")
    public String getChat(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        model.addAttribute("user", userService.findUserByEmail(currentPrincipalName));
        model.addAttribute("chats", userRepository.findAll());
        chatNotificationService.findAll();
        return "chat/chat";
    }
    @GetMapping("/chat/{id}")
    public @ResponseBody User getChatWithUser(@PathVariable("id") Long id){
        return userRepository.findById(id).get();
    }
    @GetMapping("/chat/messages")
    public @ResponseBody List<ChatMessage> getMessagesWithUser(@RequestParam Long authedUser, @RequestParam Long toUser){
        return chatMessageService.findChatMessages(authedUser, toUser);
    }
    @GetMapping("/fetchAllUsers")
    public @ResponseBody List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
