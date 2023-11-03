package com.kakaoseventeen.dogwalking.application.dto;

import lombok.Builder;

public record CreateAppReqDTO(
        Long memberId,
        Long notificationId,
        String title,
        String aboutMe,
        String certification,
        String experience
) {
    @Builder
    public CreateAppReqDTO {

    }
}
