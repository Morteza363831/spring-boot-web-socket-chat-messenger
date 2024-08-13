package academy.learnprograming.springbootwebsocketchatmessenger.controller;


import academy.learnprograming.springbootwebsocketchatmessenger.model.ChatMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@Component
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    public WebSocketEventListener(SimpMessageSendingOperations simpMessageSendingOperations) {
        this.simpMessageSendingOperations = simpMessageSendingOperations;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent sessionConnectedEvent) {
        log.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionConnectedEvent event) {

        StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) stompHeaderAccessor.getSessionAttributes().get("username");

        if (username != null) {
            log.info("User Disconnected : " + username);
            ChatMessages chatMessages = new ChatMessages();
            chatMessages.setType(ChatMessages.MessageType.LEAVE);
            chatMessages.setSender(username);

            simpMessageSendingOperations.convertAndSend("/topic/public", chatMessages);
        }
    }

}
