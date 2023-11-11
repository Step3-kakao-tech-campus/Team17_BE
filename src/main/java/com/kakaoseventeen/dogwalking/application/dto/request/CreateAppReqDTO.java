package com.kakaoseventeen.dogwalking.application.dto.request;

import lombok.Builder;

/**
 * CreateAppReqDTO : 지원서 작성 요청 DTO
 *
 * @author 박영규
 * @version 1.0
 */
public record CreateAppReqDTO(
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
