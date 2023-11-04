package com.kakaoseventeen.dogwalking._core.utils.exception;

import com.kakaoseventeen.dogwalking._core.utils.MessageCode;
import lombok.Getter;

@Getter
public class CoinNotEnoughException extends RuntimeException{
    public final MessageCode messageCode;

    public CoinNotEnoughException(MessageCode messageCode) {
        this.messageCode = messageCode;
    }
}
