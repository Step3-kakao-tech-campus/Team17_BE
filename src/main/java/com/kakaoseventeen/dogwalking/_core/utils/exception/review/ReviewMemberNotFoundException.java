package com.kakaoseventeen.dogwalking._core.utils.exception.review;

import com.kakaoseventeen.dogwalking._core.utils.ReviewMessageCode;
import lombok.Getter;

@Getter
public class ReviewMemberNotFoundException extends IllegalArgumentException{

    private ReviewMessageCode reviewMessageCode;

    public ReviewMemberNotFoundException(ReviewMessageCode reviewMessageCode){
        this.reviewMessageCode = reviewMessageCode;
    }
}
