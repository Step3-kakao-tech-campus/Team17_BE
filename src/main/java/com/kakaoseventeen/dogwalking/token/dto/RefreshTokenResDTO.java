package com.kakaoseventeen.dogwalking.token.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Refresh Token으로 Access Token 재발급 응답 DTO
 *
 * @author 곽민주
 * @version 1.0
 */
@Getter
@Setter
public class RefreshTokenResDTO {
    String accessToken;

    public RefreshTokenResDTO(String accessToken){
        this.accessToken = accessToken;
    }
}
