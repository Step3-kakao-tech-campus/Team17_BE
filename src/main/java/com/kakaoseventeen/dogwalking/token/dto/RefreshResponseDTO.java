package com.kakaoseventeen.dogwalking.token.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshResponseDTO {
    String accessToken;

    public RefreshResponseDTO(String accessToken){
        this.accessToken = accessToken;
    }
}
