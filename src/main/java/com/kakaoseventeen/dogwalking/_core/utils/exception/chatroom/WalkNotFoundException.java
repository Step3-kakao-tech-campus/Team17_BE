package com.kakaoseventeen.dogwalking._core.utils.exception.chatroom;

import com.kakaoseventeen.dogwalking._core.utils.ChatRoomMessageCode;
import lombok.Getter;

@Getter
public class WalkNotFoundException extends RuntimeException{
    private ChatRoomMessageCode chatRoomMessageCode;

    public WalkNotFoundException(ChatRoomMessageCode chatRoomMessageCode){
        this.chatRoomMessageCode=chatRoomMessageCode;
    }
}
