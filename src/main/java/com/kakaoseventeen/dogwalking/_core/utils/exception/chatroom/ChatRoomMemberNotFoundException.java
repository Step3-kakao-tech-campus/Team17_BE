package com.kakaoseventeen.dogwalking._core.utils.exception.chatroom;

import com.kakaoseventeen.dogwalking._core.utils.ChatRoomMessageCode;
import lombok.Getter;

@Getter
public class ChatRoomMemberNotFoundException extends IllegalArgumentException{

    private ChatRoomMessageCode chatRoomMessageCode;

    public ChatRoomMemberNotFoundException(ChatRoomMessageCode chatRoomMessageCode){
        this.chatRoomMessageCode = chatRoomMessageCode;
    }

}
