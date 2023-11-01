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

    @ExceptionHandler(InvalidEmailFormatException.class)
    public ApiResponse<ApiResponse.CustomBody> handleIllegalStateException(InvalidEmailFormatException e){
        return ApiResponseGenerator.fail(MemberMessageCode.INVALID_EMAIL_FORMAT.getValue(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidPasswordFormatException.class)
    public ApiResponse<ApiResponse.CustomBody> handleIllegalStateException(InvalidPasswordFormatException e){
        return ApiResponseGenerator.fail(MemberMessageCode.INVALID_PASSWORD_FORMAT.getValue(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidPasswordLengthException.class)
    public ApiResponse<ApiResponse.CustomBody> handleIllegalStateException(InvalidPasswordLengthException e){
        return ApiResponseGenerator.fail(MemberMessageCode.INVALID_PASSWORD_LENGTH.getValue(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DuplicateEmailException.class)
    public ApiResponse<ApiResponse.CustomBody> handleIllegalStateException(DuplicateEmailException e){
        return ApiResponseGenerator.fail(MemberMessageCode.DUPLICATE_EMAIL.getValue(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MemberNotExistException.class)
    public ApiResponse<ApiResponse.CustomBody> handleIllegalStateException(MemberNotExistException e){
        return ApiResponseGenerator.fail(MemberMessageCode.MEMBER_NOT_EXIST.getValue(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ApiResponse<ApiResponse.CustomBody> handleIllegalStateException(PasswordNotMatchException e){
        return ApiResponseGenerator.fail(MemberMessageCode.PASSWORD_NOT_MATCH.getValue(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ApiResponse<ApiResponse.CustomBody> handleIllegalStateException(ValidationException e){
        return ApiResponseGenerator.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotificationException.class)
    public ApiResponse<ApiResponse.CustomBody> handleIllegalStateException(NotificationException e){
        return ApiResponseGenerator.fail(MessageCode.NOTIFICATION_NOT_EXIST.getValue(), HttpStatus.BAD_REQUEST);
    }


/*
    @ExceptionHandler(SecurityFilterException.class)
    public ApiResponse<ApiResponse.CustomBody> handleIllegalStateException(SecurityFilterException e){
        return ApiResponseGenerator.fail(MemberMessageCode.REQUIRES_AUTHENTICATION.getValue(), HttpStatus.UNAUTHORIZED);
    }
*/



}
