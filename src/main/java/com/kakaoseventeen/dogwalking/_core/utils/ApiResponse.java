package com.kakaoseventeen.dogwalking._core.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

/**
 * ApiResponse : Response 되는 객체들을 공통적인 형식으로 묶어서 클라이언트에게 보낼 수 있는 자료구조
 * @author 승건 이
 * @version 1.0
 */
@Getter
public class ApiResponse<B> extends ResponseEntity<B> {

    public ApiResponse(B body, HttpStatus status) {
        super(body, status);
    }

    public ApiResponse(B body, MediaType mediaType, HttpStatus status) {
        super(body, status);
        this.getHeaders().setContentType(mediaType);
    }

    @Getter
    @AllArgsConstructor
    public static class CustomBody<D> implements Serializable {
        private Boolean success;
        private D response;
        private Error error;
    }
}
