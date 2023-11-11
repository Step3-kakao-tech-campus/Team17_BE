package com.kakaoseventeen.dogwalking._core.utils;

import lombok.Getter;

/**
 * ChatRoomMessageCode : 에러의 메세지 내용들을 제공하는 enum 클래스
 *
 * @author 박영규
 * @version 1.0
 */
@Getter
public enum ChatRoomMessageCode {

    MEMBER_NOT_FOUND("사용자가 존재하지 않아 채팅방 생성에 실패하였습니다."),
    MATCH_NOT_FOUND("일치하는 매칭 결과가 존재하지 않아 채팅방 생성에 실패하였습니다."),
    CHAT_MESSAGE_NOT_FOUND("채팅 내역이 비어있습니다."),
    WALK_NOT_FOUND("일치하는 산책 내역이 존재하지 않습니다."),
    INVALID_MEMBER("인증된 사용자 접근이 아닙니다.");
    private final String value;

    ChatRoomMessageCode(String value) {
        this.value = value;
    }
}
