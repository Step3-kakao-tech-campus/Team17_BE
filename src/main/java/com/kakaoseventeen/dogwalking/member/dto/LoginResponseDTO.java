package com.kakaoseventeen.dogwalking.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class LoginResponseDTO {
    private String accessToken;
    private String refreshToken;

    public LoginResponseDTO(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}