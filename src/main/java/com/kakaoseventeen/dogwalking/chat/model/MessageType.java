package com.kakaoseventeen.dogwalking.chat.model;

public enum MessageType {
    CHAT("CHAT"),
    IMAGE("IMAGE"),
    JOIN("JOIN"),
    LEAVE("LEAVE");

    private final String messageType;

    private MessageType(String messageType){
        this.messageType=messageType;
    }

}
