package com.kakaoseventeen.dogwalking._core.utils.exception.chatMessage;

import com.kakaoseventeen.dogwalking._core.utils.ChatMessageCode;
import lombok.Getter;

@Getter
public class MemberIdNotFoundException extends RuntimeException{

    private ChatMessageCode chatMessageCode;

    public MemberIdNotFoundException(ChatMessageCode chatMessageCode){
        this.chatMessageCode = chatMessageCode;
    }
}
