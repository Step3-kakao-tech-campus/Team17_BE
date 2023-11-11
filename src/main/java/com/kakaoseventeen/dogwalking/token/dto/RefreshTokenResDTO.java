package com.kakaoseventeen.dogwalking.token.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenResDTO {
    String accessToken;

    public RefreshTokenResDTO(String accessToken){
        this.accessToken = accessToken;
    }
}
