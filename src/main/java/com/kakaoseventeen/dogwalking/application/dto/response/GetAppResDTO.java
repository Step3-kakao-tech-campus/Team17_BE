package com.kakaoseventeen.dogwalking.application.dto.response;

import lombok.Builder;

/**
 * GetAppResDTO : 지원서 조회 응답 DTO
 *
 * @author 박영규
 * @version 1.0
 */
public record GetAppResDTO(
        String title,
        Long memberId,
        String memberNickname,
        Long notificationId,
        String memberImage,
        String aboutMe,
        String certification,
        String experience
) {
    @Builder
    public GetAppResDTO{

    }
}
