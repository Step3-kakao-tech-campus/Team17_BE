package com.kakaoseventeen.dogwalking._core.utils.exception;

import com.kakaoseventeen.dogwalking._core.utils.MemberMessageCode;
import lombok.Getter;

@Getter
public class PasswordNotMatchException extends RuntimeException{
    public final MemberMessageCode memberMessageCode;

    public PasswordNotMatchException(MemberMessageCode messageCode) {
        this.memberMessageCode = messageCode;
    }
}
