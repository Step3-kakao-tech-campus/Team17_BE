package com.kakaoseventeen.dogwalking._core.utils.exception.chatroom;

import com.kakaoseventeen.dogwalking._core.utils.ChatRoomMessageCode;

public class ChatMessageNotFoundException extends RuntimeException{

    private ChatRoomMessageCode chatRoomMessageCode;

    public ChatMessageNotFoundException(ChatRoomMessageCode chatRoomMessageCode){
        this.chatRoomMessageCode=chatRoomMessageCode;
    }
}
