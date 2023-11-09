package com.kakaoseventeen.dogwalking._core.utils.exception.application;

import com.kakaoseventeen.dogwalking._core.utils.ApplicationMessageCode;
import lombok.Getter;

@Getter
public class NotificationNotFoundException extends RuntimeException{

    private ApplicationMessageCode applicationMessageCode;

    public NotificationNotFoundException(ApplicationMessageCode applicationMessageCode){
        this.applicationMessageCode = applicationMessageCode;
    }
}
