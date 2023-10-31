임package com.kakaoseventeen.dogwalking.member.dto;


import lombok.Builder;

public record SignupReqDTO(
        String email,
        String password,
        String nickname
) {
    @Builder
    public SignupReqDTO{

    }
}
