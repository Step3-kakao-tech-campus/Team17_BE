package com.kakaoseventeen.dogwalking.chat.dto;

import lombok.Builder;

public record ChatListResDTO(
        Long chatRoomId,
        Long userId,
        String userImage,
        String chatContent,
        String walkType,
        Long matchId
) {
    @Builder
    public ChatListResDTO{

    }
}
