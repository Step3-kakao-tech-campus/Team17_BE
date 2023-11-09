package com.kakaoseventeen.dogwalking._core.utils.exception.member;

import com.kakaoseventeen.dogwalking._core.utils.MemberMessageCode;
import lombok.Getter;

@Getter
public class InvalidPasswordFormatException extends IllegalArgumentException {

    public final MemberMessageCode memberMessageCode;

    public InvalidPasswordFormatException(MemberMessageCode memberMessageCode) {
        this.memberMessageCode = memberMessageCode;
    }
}
