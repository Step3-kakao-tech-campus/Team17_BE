package com.kakaoseventeen.dogwalking._core.utils.exception.review;

import com.kakaoseventeen.dogwalking._core.utils.ReviewMessageCode;
import lombok.Getter;

@Getter
public class NotificationIdNotExistException extends IllegalArgumentException{
    private ReviewMessageCode messageCode;

    public NotificationIdNotExistException(ReviewMessageCode messageCode){
        this.messageCode = messageCode;
    }
}
