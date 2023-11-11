package com.kakaoseventeen.dogwalking._core.utils.exception.chatMessage;

import com.kakaoseventeen.dogwalking._core.utils.ChatMessageCode;
import lombok.Getter;

@Getter
public class ChatRoomNotFoundException extends IllegalArgumentException{

    private ChatMessageCode chatMessageCode;

    public ChatRoomNotFoundException(ChatMessageCode chatMessageCode){
        this.chatMessageCode=chatMessageCode;
    }
}
