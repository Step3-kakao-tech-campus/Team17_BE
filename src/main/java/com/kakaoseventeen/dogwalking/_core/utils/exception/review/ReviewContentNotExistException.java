package com.kakaoseventeen.dogwalking._core.utils.exception.review;

import com.kakaoseventeen.dogwalking._core.utils.ReviewMessageCode;
import lombok.Getter;

@Getter
public class ReviewContentNotExistException extends IllegalArgumentException{

    private final ReviewMessageCode messageCode;

    public ReviewContentNotExistException(ReviewMessageCode messageCode){
        this.messageCode=messageCode;
    }
}
