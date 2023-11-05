package com.kakaoseventeen.dogwalking._core.utils.exception;

import com.kakaoseventeen.dogwalking._core.utils.MessageCode;
import lombok.Getter;

@Getter
public class NotificationException extends RuntimeException {

    public final MessageCode messageCode;

    public NotificationException(MessageCode messageCode) {
        this.messageCode = messageCode;
    }

}