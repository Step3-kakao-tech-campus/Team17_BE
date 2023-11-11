package com.kakaoseventeen.dogwalking.review.dto;

import lombok.Builder;

/**
 * WriteReviewReqDTO(리뷰 작성 요청) DTO
 *
 * @author 박영규
 * @version 1.0
 */
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
