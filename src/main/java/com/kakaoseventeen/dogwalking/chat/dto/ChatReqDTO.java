package com.kakaoseventeen.dogwalking.chat.dto;

import lombok.Builder;

public record ChatReqDTO(
        MessageTypeDTO messageType,
        Long memberId,
        String chatContent

) {
    @Builder
    public ChatReqDTO{

    }
}
