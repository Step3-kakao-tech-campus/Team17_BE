package com.kakaoseventeen.dogwalking.application.dto;

import lombok.Builder;

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
