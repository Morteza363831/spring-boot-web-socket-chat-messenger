package org.examples.springbootwebsocketchatmessenger.controller;

import org.examples.springbootwebsocketchatmessenger.entity.ChatMessages;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChatController {

    @GetMapping("/chat")
    public String chat(@RequestParam String username) {
        return "chat";
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessages sendMessage(@Payload ChatMessages chatMessages) {
        return chatMessages;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessages addUser(@Payload ChatMessages chatMessages,
                                SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        simpMessageHeaderAccessor.getSessionAttributes().put("username",chatMessages.getSender());
        return chatMessages;
    }


}
