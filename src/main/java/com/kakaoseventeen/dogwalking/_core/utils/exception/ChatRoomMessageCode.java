package com.kakaoseventeen.dogwalking._core.utils.exception;

import lombok.Getter;

@Getter
public enum ChatRoomMessageCode {

    MEMBER_NOT_FOUND("사용자가 존재하지 않아 채팅방 생성에 실패하였습니다."),
    MATCH_NOT_FOUND("일치하는 매칭 결과가 존재하지 않아 채팅방 생성에 실패하였습니다.");
    private final String value;

    ChatRoomMessageCode(String value) {
        this.value = value;
    }
}
