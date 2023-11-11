package com.kakaoseventeen.dogwalking.member.dto.response;

import lombok.Getter;

/**
 * LoginRespDTO : 로그인 응답 DTO
 *
 * @author 곽민주
 * @version 1.0
 */
@Getter
public class LoginRespDTO {
    private String accessToken;
    private String refreshToken;

    public LoginRespDTO(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}