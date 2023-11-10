package com.kakaoseventeen.dogwalking._core.utils.exception.chatroom;

import com.kakaoseventeen.dogwalking._core.utils.ChatRoomMessageCode;
import lombok.Getter;

@Getter
public class ChatRoomMatchNotFoundException extends IllegalArgumentException{

    private ChatRoomMessageCode chatRoomMessageCode;

    public ChatRoomMatchNotFoundException(ChatRoomMessageCode chatRoomMessageCode){
        this.chatRoomMessageCode = chatRoomMessageCode;
    }
}
