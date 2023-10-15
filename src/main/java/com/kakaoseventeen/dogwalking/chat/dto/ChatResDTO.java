package com.kakaoseventeen.dogwalking.chat.dto;

import lombok.Builder;

import java.time.LocalDateTime;

public record ChatResDTO(
        Long chatId,
        Long userId,
        String chatContent,
        MessageTypeDTO messageType,
        LocalDateTime contentTime
) {
    @Builder
    public ChatResDTO{

    }
}
