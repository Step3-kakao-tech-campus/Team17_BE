package com.kakaoseventeen.dogwalking._core.utils.exception.application;

import com.kakaoseventeen.dogwalking._core.utils.ApplicationMessageCode;
import lombok.Getter;

@Getter
public class ApplicationNotFoundException extends IllegalArgumentException{
    private ApplicationMessageCode applicationMessageCode;

    public ApplicationNotFoundException(ApplicationMessageCode applicationMessageCode){
        this.applicationMessageCode=applicationMessageCode;
    }
}
