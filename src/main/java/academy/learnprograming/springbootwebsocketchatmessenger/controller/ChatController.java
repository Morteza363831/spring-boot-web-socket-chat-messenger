package academy.learnprograming.springbootwebsocketchatmessenger.controller;

import academy.learnprograming.springbootwebsocketchatmessenger.model.ChatMessages;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

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
