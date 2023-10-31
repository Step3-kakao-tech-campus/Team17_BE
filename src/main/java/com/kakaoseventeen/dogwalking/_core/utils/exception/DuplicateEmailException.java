package com.kakaoseventeen.dogwalking._core.utils.exception;

import com.kakaoseventeen.dogwalking._core.utils.MemberMessageCode;
import lombok.Getter;

@Getter
public class DuplicateEmailException extends RuntimeException{
    public final MemberMessageCode memberMessageCode;
    public DuplicateEmailException(MemberMessageCode memberMessageCode) {
        this.memberMessageCode = memberMessageCode;
    }
}
