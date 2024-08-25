package org.examples.springbootwebsocketchatmessenger.entity;

import lombok.Data;

@Data
public class ChatMessages {

    private MessageType type;
    private String content;
    private String sender;


    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

}
