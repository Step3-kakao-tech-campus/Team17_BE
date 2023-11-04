package com.kakaoseventeen.dogwalking.token.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshRespDTO {
    String accessToken;

    public RefreshRespDTO(String accessToken){
        this.accessToken = accessToken;
    }
}
