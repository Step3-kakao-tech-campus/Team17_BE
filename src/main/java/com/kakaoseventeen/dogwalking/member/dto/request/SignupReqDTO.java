package com.kakaoseventeen.dogwalking.member.dto.request;


import lombok.Builder;

/**
 * SignupReqDTO : 회원가입 요청 DTO
 *
 * @author 박영규
 * @version 1.0
 */
public record SignupReqDTO(
        String email,
        String password,
        String nickname
) {
    @Builder
    public SignupReqDTO{

    }
}
