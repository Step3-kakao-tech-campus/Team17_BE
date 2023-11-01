package com.kakaoseventeen.dogwalking._core.utils.exception;

import com.kakaoseventeen.dogwalking._core.utils.MessageCode;

public class NotificationUpdateException extends RuntimeException {
    public final MessageCode messageCode;

    public NotificationUpdateException(MessageCode messageCode) {
        this.messageCode = messageCode;
    }
}
