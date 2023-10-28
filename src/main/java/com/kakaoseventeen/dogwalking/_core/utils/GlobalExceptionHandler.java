package com.kakaoseventeen.dogwalking._core.utils;

import com.kakaoseventeen.dogwalking._core.utils.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WalkNotExistException.class)
    public ApiResponse<ApiResponse.CustomBody> handleIllegalStateException(WalkNotExistException e){
        return ApiResponseGenerator.fail(MessageCode.WALK_NOT_EXIST.getValue(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MatchNotExistException.class)
    public ApiResponse<ApiResponse.CustomBody> handleIllegalStateException(MatchNotExistException e){
        return ApiResponseGenerator.fail(MessageCode.MATCH_NOT_EXIST.getValue(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MemberNotExistException.class)
    public ApiResponse<ApiResponse.CustomBody> handleIllegalStateException(MemberNotExistException e){
        return ApiResponseGenerator.fail(MessageCode.MEMBER_NOT_EXIST.getValue(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ApiResponse<ApiResponse.CustomBody> handleIllegalStateException(ValidationException e){
        return ApiResponseGenerator.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
