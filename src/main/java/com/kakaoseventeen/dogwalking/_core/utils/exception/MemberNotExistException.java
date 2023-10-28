package com.kakaoseventeen.dogwalking._core.utils.exception;

import com.kakaoseventeen.dogwalking._core.utils.MessageCode;
import lombok.Getter;

@Getter
public class MemberNotExistException extends RuntimeException {

    public final MessageCode messageCode;

    public MemberNotExistException(MessageCode messageCode) {
        this.messageCode = messageCode;
    }

}