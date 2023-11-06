package com.kakaoseventeen.dogwalking._core.utils.exception;

import com.kakaoseventeen.dogwalking._core.utils.MemberMessageCode;
import com.kakaoseventeen.dogwalking._core.utils.MessageCode;
import lombok.Getter;

@Getter
public class DuplicateNotificationWithWalkException extends RuntimeException{

    public final MessageCode messageCode;

    public DuplicateNotificationWithWalkException(MessageCode messageCode) {
        this.messageCode = messageCode;
    }

}
