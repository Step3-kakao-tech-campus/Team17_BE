package com.kakaoseventeen.dogwalking._core.utils.exception.notification;

import com.kakaoseventeen.dogwalking._core.utils.MessageCode;
import lombok.Getter;

@Getter
public class NotificationUpdateException extends RuntimeException {

    public final MessageCode messageCode;

    public NotificationUpdateException(MessageCode messageCode) {
        this.messageCode = messageCode;
    }
}
