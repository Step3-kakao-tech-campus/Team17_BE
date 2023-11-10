package com.kakaoseventeen.dogwalking._core.utils;

import com.kakaoseventeen.dogwalking._core.utils.exception.*;
import com.kakaoseventeen.dogwalking._core.utils.exception.application.ApplicationMemberNotFoundException;
import com.kakaoseventeen.dogwalking._core.utils.exception.application.NotificationNotFoundException;
import com.kakaoseventeen.dogwalking._core.utils.exception.chatMessage.ChatRoomNotFoundException;
import com.kakaoseventeen.dogwalking._core.utils.exception.chatMessage.MemberIdNotFoundException;
import com.kakaoseventeen.dogwalking._core.utils.exception.chatroom.*;
import com.kakaoseventeen.dogwalking._core.utils.exception.member.*;
import com.kakaoseventeen.dogwalking._core.utils.exception.notification.*;
import com.kakaoseventeen.dogwalking._core.utils.exception.review.*;
import com.kakaoseventeen.dogwalking._core.utils.exception.walk.DuplicateNotificationWithWalkException;
import com.kakaoseventeen.dogwalking._core.utils.exception.walk.WalkNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * GlobalExceptionHandler : @RestControllerAdvice를 이용해 전역적으로 에러를 핸들링 하는 클래스
 *
 * @author 승건 이
 * @version 1.0
 */
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

    @ExceptionHandler(DuplicateNotificationWithWalkException.class)
    public ApiResponse<ApiResponse.CustomBody> handleIllegalStateException(DuplicateNotificationWithWalkException e){
        return ApiResponseGenerator.fail(MessageCode.DUPLICATE_NOTIFICATION.getValue(), HttpStatus.BAD_REQUEST);
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


    @ExceptionHandler(DogListNotExistException.class)
    public ApiResponse<ApiResponse.CustomBody> handleIllegalStateException(DogListNotExistException e){
        return ApiResponseGenerator.fail(MessageCode.DOG_LIST_NOT_EXIST.getValue(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DogNotExistException.class)
    public ApiResponse<ApiResponse.CustomBody> handleIllegalStateException(DogNotExistException e){
        return ApiResponseGenerator.fail(MessageCode.DOG_NOT_EXIST.getValue(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(CoinNotEnoughException.class)
    public ApiResponse<ApiResponse.CustomBody> handleIllegalStateException(CoinNotEnoughException e){
        return ApiResponseGenerator.fail(MessageCode.MUNG_COIN_NOT_ENOUGH.getValue(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotificationUpdateException.class)
    public ApiResponse<ApiResponse.CustomBody> handleIllegalStateException(NotificationUpdateException e){
        return ApiResponseGenerator.fail(MessageCode.NOTIFICATION_FORBIDDEN.getValue(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(RefreshTokenNotExistException.class)
    public ApiResponse<ApiResponse.CustomBody> handleIllegalStateException(RefreshTokenNotExistException e){
        return ApiResponseGenerator.fail(MemberMessageCode.REFRESH_TOKEN_NOT_EXIST.getValue(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RefreshTokenExpiredException.class)
    public ApiResponse<ApiResponse.CustomBody> handleIllegalStateException(RefreshTokenExpiredException e){
        return ApiResponseGenerator.fail(MemberMessageCode.REFRESH_TOKEN_EXPIRED.getValue(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotificationTimeException.class)
    public ApiResponse<ApiResponse.CustomBody> handleIllegalStateException(NotificationTimeException e){
        return ApiResponseGenerator.fail(MessageCode.NOTIFICATION_TIME_ERROR.getValue(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ImageNotExistException.class)
    public ApiResponse<ApiResponse.CustomBody> handleIllegalStateException(ImageNotExistException e){
        return ApiResponseGenerator.fail(MessageCode.IMAGE_NOT_EXIST.getValue(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ChatRoomMemberNotFoundException.class)
    public ApiResponse<ApiResponse.CustomBody> handleIllegalArgumentException(ChatRoomMemberNotFoundException e){
        return ApiResponseGenerator.fail(ChatRoomMessageCode.MEMBER_NOT_FOUND.getValue(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ChatRoomMatchNotFoundException.class)
    public ApiResponse<ApiResponse.CustomBody> hadnleIllegalArgumentException(ChatRoomMatchNotFoundException e){
        return ApiResponseGenerator.fail(ChatRoomMessageCode.MATCH_NOT_FOUND.getValue(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReviewMemberNotFoundException.class)
    public ApiResponse<ApiResponse.CustomBody> handleIllegalArgumentException(ReviewMemberNotFoundException e){
        return ApiResponseGenerator.fail(ReviewMessageCode.REVIEW_MEMBER_NOT_FOUND.getValue(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotMyNotificationException.class)
    public ApiResponse<ApiResponse.CustomBody> handleIllegalStateException(NotMyNotificationException e){
        return ApiResponseGenerator.fail(MessageCode.NOT_MY_NOTIFICATION.getValue(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ChatRoomNotFoundException.class)
    public ApiResponse<ApiResponse.CustomBody> handleIllegalStateException(ChatRoomNotFoundException e){
        return ApiResponseGenerator.fail(ChatMessageCode.CHATROOM_NOT_FOUND.getValue(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MemberIdNotFoundException.class)
    public ApiResponse<ApiResponse.CustomBody> handleRuntimeException(MemberIdNotFoundException e){
        return ApiResponseGenerator.fail(ChatMessageCode.MEMBER_ID_NOT_FOUND.getValue(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ApplicationMemberNotFoundException.class)
    public ApiResponse<ApiResponse.CustomBody> handleRuntimeException(ApplicationMemberNotFoundException e){
        return ApiResponseGenerator.fail(ApplicationMessageCode.MEMBER_NOT_FOUND.getValue(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotificationNotFoundException.class)
    public ApiResponse<ApiResponse.CustomBody> handleRuntimeException(NotificationNotFoundException e){
        return ApiResponseGenerator.fail(ApplicationMessageCode.NOTIFICATION_NOT_FOUND.getValue(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MemberIdNotExistException.class)
    public ApiResponse<ApiResponse.CustomBody> handleIllegalArgumentException(MemberIdNotExistException e){
        return ApiResponseGenerator.fail(ReviewMessageCode.MEMBER_ID_NOT_EXIST.getValue(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotificationIdNotExistException.class)
    public ApiResponse<ApiResponse.CustomBody> handleIllegalArgumentException(NotificationIdNotExistException e){
        return ApiResponseGenerator.fail(ReviewMessageCode.NOTIFICATION_ID_NOT_EXIST.getValue(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReceiveMemberIdNotExistException.class)
    public ApiResponse<ApiResponse.CustomBody> handleIllegalArgumentException(ReceiveMemberIdNotExistException e){
        return ApiResponseGenerator.fail(ReviewMessageCode.RECEIVE_MEMBER_ID_NOT_EXIST.getValue(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReviewContentNotExistException.class)
    public ApiResponse<ApiResponse.CustomBody> handleIllegalArgumentException(ReviewContentNotExistException e){
        return ApiResponseGenerator.fail(ReviewMessageCode.REVIEW_CONTENT_NOT_EXIST.getValue(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WalkNotFoundException.class)
    public ApiResponse<ApiResponse.CustomBody> handleRuntimeException(WalkNotFoundException e){
        return ApiResponseGenerator.fail(ChatRoomMessageCode.WALK_NOT_FOUND.getValue(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ChatMessageNotFoundException.class)
    public ApiResponse<ApiResponse.CustomBody> handleRuntimeException(ChatMessageNotFoundException e){
        return ApiResponseGenerator.fail(ChatRoomMessageCode.CHAT_MESSAGE_NOT_FOUND.getValue(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidMemberException.class)
    public ApiResponse<ApiResponse.CustomBody> handleRuntimeException(InvalidMemberException e){
        return ApiResponseGenerator.fail(ChatRoomMessageCode.INVALID_MEMBER.getValue(), HttpStatus.UNAUTHORIZED);
    }
}
