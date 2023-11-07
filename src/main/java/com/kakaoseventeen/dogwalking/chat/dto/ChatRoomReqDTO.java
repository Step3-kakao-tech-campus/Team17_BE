package com.kakaoseventeen.dogwalking.chat.dto;

import lombok.Builder;

public record ChatRoomReqDTO(
        Long notiMemberId,
        Long appMemberId,
        Long matchId
) {
    @Builder
    public ChatRoomReqDTO{

    }
}
