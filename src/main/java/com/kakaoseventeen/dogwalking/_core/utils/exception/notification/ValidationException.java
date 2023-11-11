package com.kakaoseventeen.dogwalking._core.utils.exception.notification;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {

    public final String message;

    public ValidationException(String message) {
        this.message = message;
    }

}
