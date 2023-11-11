package com.kakaoseventeen.dogwalking._core.utils.exception.member;

import com.kakaoseventeen.dogwalking._core.utils.MemberMessageCode;
import lombok.Getter;

@Getter
public class RefreshTokenNotExistException extends RuntimeException{

    public final MemberMessageCode messageCode;

    public RefreshTokenNotExistException(MemberMessageCode messageCode) {
        this.messageCode = messageCode;
    }
}
