package com.kakaoseventeen.dogwalking.review.dto;

import lombok.Builder;

public record GetReviewResDTO(
        String memberNickname,
        String memberImage
) {
    @Builder
    public GetReviewResDTO{

    }
}
