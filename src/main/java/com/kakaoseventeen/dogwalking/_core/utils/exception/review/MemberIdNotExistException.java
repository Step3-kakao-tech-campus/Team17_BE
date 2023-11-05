package com.kakaoseventeen.dogwalking._core.utils.exception.review;

import com.kakaoseventeen.dogwalking._core.utils.ReviewMessageCode;
import lombok.Getter;

@Getter
public class MemberIdNotExistException extends IllegalArgumentException{

    public final ReviewMessageCode messageCode;

    public MemberIdNotExistException(ReviewMessageCode messageCode) {
        this.messageCode = messageCode;
    }

}
