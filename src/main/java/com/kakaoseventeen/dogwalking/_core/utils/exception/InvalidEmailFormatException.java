package com.kakaoseventeen.dogwalking._core.utils.exception;

import com.kakaoseventeen.dogwalking._core.utils.MemberMessageCode;
import lombok.Getter;

@Getter
public class InvalidEmailFormatException extends IllegalArgumentException{
    public final MemberMessageCode memberMessageCode;

    public InvalidEmailFormatException(MemberMessageCode memberMessageCode) {
        this.memberMessageCode = memberMessageCode;
    }
}
