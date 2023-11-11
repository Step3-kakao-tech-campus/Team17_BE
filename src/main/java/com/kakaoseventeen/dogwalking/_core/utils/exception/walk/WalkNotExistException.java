package com.kakaoseventeen.dogwalking._core.utils.exception.walk;

import com.kakaoseventeen.dogwalking._core.utils.MessageCode;
import lombok.Getter;

@Getter
public class WalkNotExistException extends RuntimeException {

    public final MessageCode messageCode;

    public WalkNotExistException(MessageCode messageCode) {
        this.messageCode = messageCode;
    }

}
