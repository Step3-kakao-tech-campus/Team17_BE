package com.kakaoseventeen.dogwalking._core.utils.exception;

import com.kakaoseventeen.dogwalking._core.utils.MemberMessageCode;
import com.kakaoseventeen.dogwalking._core.utils.MessageCode;
import lombok.Getter;

@Getter
public class MemberNotExistException extends RuntimeException {

    public final MemberMessageCode memberMessageCode;

    public MemberNotExistException(MemberMessageCode messageCode) {
        this.memberMessageCode = messageCode;
    }

}