package com.kakaoseventeen.dogwalking.chat.dto;

import lombok.Builder;

public record ChatReqDTO(
        Long memberId,
        Long chatRoomId,
        String content,
        String contentType
) {
    @Builder
    public ChatReqDTO{

    }
}
