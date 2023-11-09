package com.kakaoseventeen.dogwalking.application.dto;

import lombok.Builder;

public record GetAppMemberResDTO(
        String memberNickname,
        String memberImage
) {
    @Builder
    public GetAppMemberResDTO{

    }
}
