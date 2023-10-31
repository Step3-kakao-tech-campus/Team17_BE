package com.kakaoseventeen.dogwalking._core.utils.exception;

import com.kakaoseventeen.dogwalking._core.utils.MessageCode;

public class DogNotExistException extends RuntimeException{

        public final MessageCode messageCode;

        public DogNotExistException(MessageCode messageCode) {
            this.messageCode = messageCode;
        }
}
