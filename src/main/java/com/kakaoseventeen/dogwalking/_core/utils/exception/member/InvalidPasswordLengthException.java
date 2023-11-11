package com.kakaoseventeen.dogwalking._core.utils.exception.member;

import com.kakaoseventeen.dogwalking._core.utils.MemberMessageCode;
import lombok.Getter;

@Getter
public class InvalidPasswordLengthException extends IllegalArgumentException {

    public final MemberMessageCode memberMessageCode;

    public InvalidPasswordLengthException(MemberMessageCode memberMessageCode) {
        this.memberMessageCode = memberMessageCode;
    }
}
