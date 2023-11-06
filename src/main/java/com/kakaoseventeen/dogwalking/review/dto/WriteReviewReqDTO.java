package com.kakaoseventeen.dogwalking.review.dto;

import lombok.Builder;

public record WriteReviewReqDTO(
        Long memberId,
        Long receiveMemberId,
        String reviewContent,
        ReviewEvalDTO reviewEval,
        Long notificationId,
        Integer dogBowl
) {
    @Builder
    public WriteReviewReqDTO{

    }
}
