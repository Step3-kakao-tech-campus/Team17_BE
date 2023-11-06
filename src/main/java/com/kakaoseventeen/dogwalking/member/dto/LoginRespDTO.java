package com.kakaoseventeen.dogwalking.member.dto;

import lombok.Getter;

@Getter
public class LoginRespDTO {
    private String accessToken;
    private String refreshToken;

    public LoginRespDTO(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}