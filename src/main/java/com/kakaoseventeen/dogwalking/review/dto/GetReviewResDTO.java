package com.kakaoseventeen.dogwalking.review.dto;

import lombok.Builder;

/**
 * GetReviewResDTO(리뷰 조회 응답) DTO
 *
 * @author 박영규
 * @version 1.0
 */
public record GetReviewResDTO(
        String memberNickname,
        String memberImage
) {
    @Builder
    public GetReviewResDTO{

    }
}
