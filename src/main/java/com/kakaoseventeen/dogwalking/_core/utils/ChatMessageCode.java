package com.kakaoseventeen.dogwalking._core.utils;

import lombok.Getter;

@Getter
public enum ChatMessageCode {

    CHATROOM_NOT_FOUND("일치하는 채팅방이 존재하지 않습니다."),
    MEMBER_ID_NOT_FOUND("존재하지 않는 사용자 ID 입니다.");

    private String value;

    ChatMessageCode(String value){
        this.value = value;
    }
}
