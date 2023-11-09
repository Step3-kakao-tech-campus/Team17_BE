package com.kakaoseventeen.dogwalking.chat.dto;

import com.kakaoseventeen.dogwalking.chat.domain.MessageType;
import lombok.Builder;


public record ChatMessageResDTO(
        String content,
        MessageType messageType,
        Long memberId,
        Long createdAt
) {
    @Builder
    public ChatMessageResDTO{

    }
}
