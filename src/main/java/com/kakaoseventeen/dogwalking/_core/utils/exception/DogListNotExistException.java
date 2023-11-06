package com.kakaoseventeen.dogwalking._core.utils.exception;

import com.kakaoseventeen.dogwalking._core.utils.MessageCode;
import lombok.Getter;

@Getter
public class DogListNotExistException extends RuntimeException{

        public final MessageCode messageCode;

        public DogListNotExistException(MessageCode messageCode) {
            this.messageCode = messageCode;
        }
}
