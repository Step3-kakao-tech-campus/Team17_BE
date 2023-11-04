package com.kakaoseventeen.dogwalking._core.utils.exception;

import com.kakaoseventeen.dogwalking._core.utils.MessageCode;
import lombok.Getter;

@Getter
public class PaymentNotExistException extends RuntimeException{

    public final MessageCode messageCode;

    public PaymentNotExistException(MessageCode messageCode) {
        this.messageCode = messageCode;
    }
}
