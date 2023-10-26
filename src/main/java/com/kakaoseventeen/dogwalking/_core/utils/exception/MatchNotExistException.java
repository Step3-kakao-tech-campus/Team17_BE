package com.kakaoseventeen.dogwalking._core.utils.exception;

import com.kakaoseventeen.dogwalking._core.utils.MessageCode;
import lombok.Getter;

@Getter
public class MatchNotExistException extends RuntimeException {

    public final MessageCode messageCode;

    public MatchNotExistException(MessageCode messageCode) {
        this.messageCode = messageCode;
    }

}
