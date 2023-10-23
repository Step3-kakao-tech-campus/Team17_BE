package com.kakaoseventeen.dogwalking.chat.domain;

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
