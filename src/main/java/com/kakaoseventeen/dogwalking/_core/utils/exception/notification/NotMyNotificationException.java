package com.kakaoseventeen.dogwalking._core.utils.exception.notification;

import com.kakaoseventeen.dogwalking._core.utils.MessageCode;
import lombok.Getter;

@Getter
public class NotMyNotificationException extends RuntimeException {
    public final MessageCode messageCode;

    public NotMyNotificationException(MessageCode messageCode) {
        this.messageCode = messageCode;
    }
}
