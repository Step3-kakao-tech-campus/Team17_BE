package com.kakaoseventeen.dogwalking._core.utils.exception;

import com.kakaoseventeen.dogwalking._core.utils.MessageCode;
import lombok.Getter;

@Getter
public class ImageNotExistException extends RuntimeException {

    public final MessageCode messageCode;

    public ImageNotExistException(MessageCode messageCode) {
        this.messageCode = messageCode;
    }
}
