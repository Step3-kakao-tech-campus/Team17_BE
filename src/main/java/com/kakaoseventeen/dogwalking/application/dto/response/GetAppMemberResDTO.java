package com.kakaoseventeen.dogwalking.application.dto.response;

import lombok.Builder;

/**
 * GetAppMemberResDTO : 지원서 불러오기 (멤버 정보) 응답 DTO
 *
 * @author 박영규
 * @version 1.0
 */
public record GetAppMemberResDTO(
        String memberNickname,
        String memberImage
) {
    @Builder
    public GetAppMemberResDTO{

    }
}
