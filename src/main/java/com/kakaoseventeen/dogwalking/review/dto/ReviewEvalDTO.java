package com.kakaoseventeen.dogwalking.review.dto;

import lombok.Builder;

public record ReviewEvalDTO(
        boolean eval1,
        boolean eval2,
        boolean eval3,
        boolean eval4
) {
    @Builder
    public ReviewEvalDTO{

    }
}
