package com.kakaoseventeen.dogwalking._core.utils.exception;

import com.kakaoseventeen.dogwalking._core.utils.MemberMessageCode;
import lombok.Getter;


@Getter
public class RefreshTokenExpiredException extends RuntimeException{
    public final MemberMessageCode messageCode;

    public RefreshTokenExpiredException(MemberMessageCode messageCode) {
        this.messageCode = messageCode;
    }
}
