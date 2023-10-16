package com.kakaoseventeen.dogwalking._core.utils;

import com.kakaoseventeen.dogwalking._core.utils.exception.WalkNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WalkNotExistException.class)
    public ApiResponse<ApiResponse.CustomBody> handleIllegalStateException(WalkNotExistException e){
        return ApiResponseGenerator.fail(MessageCode.WALK_NOT_EXIST.getValue(), HttpStatus.BAD_REQUEST);
    }

}
