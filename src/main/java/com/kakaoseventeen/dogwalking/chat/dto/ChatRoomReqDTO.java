package com.kakaoseventeen.dogwalking.chat.dto;

import lombok.Builder;

public record ChatRoomReqDTO(
        Long notiMemberId,
        Long appMemberId
) {
    @Builder
    public ChatRoomReqDTO{

    }
}
