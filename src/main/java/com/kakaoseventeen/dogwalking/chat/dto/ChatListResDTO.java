package com.kakaoseventeen.dogwalking.chat.dto;

import lombok.Builder;

public record ChatListResDTO(
        Long chatRoomId,
        Long memberId,
        String memberNickname,
        String memberImage,
        String chatContent,
        String walkType,
        Long matchId
) {
    @Builder
    public ChatListResDTO{

    }
}
