package com.kakaoseventeen.dogwalking._core.utils.exception.notification;

import com.kakaoseventeen.dogwalking._core.utils.MessageCode;
import lombok.Getter;

@Getter
public class DogNotExistException extends RuntimeException {

    public final MessageCode messageCode;

    public DogNotExistException(MessageCode messageCode) {
        this.messageCode = messageCode;
    }
}
