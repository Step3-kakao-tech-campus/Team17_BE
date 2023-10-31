package com.kakaoseventeen.dogwalking._core.utils.exception;

import com.kakaoseventeen.dogwalking._core.utils.MemberMessageCode;

public class PasswordNotMatchException extends RuntimeException{
    public final MemberMessageCode memberMessageCode;

    public PasswordNotMatchException(MemberMessageCode messageCode) {
        this.memberMessageCode = messageCode;
    }
}
