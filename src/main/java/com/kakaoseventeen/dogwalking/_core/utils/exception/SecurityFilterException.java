package com.kakaoseventeen.dogwalking._core.utils.exception;

public class SecurityFilterException extends RuntimeException {

    public final String message;

    public SecurityFilterException(String message) {
        this.message = message;
    }
}
