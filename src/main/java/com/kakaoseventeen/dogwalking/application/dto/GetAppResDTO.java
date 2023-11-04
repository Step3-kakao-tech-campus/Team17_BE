package com.kakaoseventeen.dogwalking.application.dto;

import lombok.Builder;

public record GetAppResDTO(
        String title,
        Long memberId,
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
