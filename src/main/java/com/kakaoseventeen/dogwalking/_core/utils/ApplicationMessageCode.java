package com.kakaoseventeen.dogwalking._core.utils;

import lombok.Getter;

@Getter
public enum ApplicationMessageCode {

    MEMBER_NOT_FOUND("사용자에 대한 정보가 존재하지 않습니다."),
    NOTIFICATION_NOT_FOUND("일치하는 공고글이 존재하지 않습니다.");

    private String value;

    ApplicationMessageCode(String value){
        this.value = value;
    }
}
