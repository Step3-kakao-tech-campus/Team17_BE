package com.kakaoseventeen.dogwalking._core.utils;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/**
 * ApiResponseGenerator : ApiResponse 자료구조 형식으로 클라이언트에게 Response를 보내는 책임
 * @author 승건 이
 * @version 1.0
 */
@UtilityClass
public class ApiResponseGenerator {

    public static ApiResponse<ApiResponse.CustomBody<Void>> success(final HttpStatus status) {
        return new ApiResponse<>(new ApiResponse.CustomBody<Void>(true,null,null),status);
    }

    public static <D> ApiResponse<ApiResponse.CustomBody<D>> success(final D response, final HttpStatus status) {
        return new ApiResponse<>(new ApiResponse.CustomBody(true, response,null), status);
    }

    public static <D> ApiResponse<ApiResponse.CustomBody<D>> success(final D response, final MediaType mediaType, final HttpStatus status) {
        return new ApiResponse<>(new ApiResponse.CustomBody(true, response,null),mediaType,status);
    }

    public static ApiResponse<ApiResponse.CustomBody> fail(String message, final HttpStatus status) {
        return new ApiResponse<>(new ApiResponse.CustomBody(false,null, new Error(message, status.toString())), status);
    }
}
