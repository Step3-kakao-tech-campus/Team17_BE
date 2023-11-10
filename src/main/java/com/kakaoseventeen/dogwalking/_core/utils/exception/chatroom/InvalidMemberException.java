package com.kakaoseventeen.dogwalking._core.utils.exception.chatroom;

import com.kakaoseventeen.dogwalking._core.utils.ChatRoomMessageCode;
import lombok.Getter;

@Getter
public class InvalidMemberException extends RuntimeException{

    private ChatRoomMessageCode chatRoomMessageCode;

    public InvalidMemberException(ChatRoomMessageCode chatRoomMessageCode){
        this.chatRoomMessageCode = chatRoomMessageCode;
    }
}
