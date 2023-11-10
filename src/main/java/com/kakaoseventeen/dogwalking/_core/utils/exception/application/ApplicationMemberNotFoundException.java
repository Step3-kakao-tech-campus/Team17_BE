package com.kakaoseventeen.dogwalking._core.utils.exception.application;

import com.kakaoseventeen.dogwalking._core.utils.ApplicationMessageCode;
import lombok.Getter;

@Getter
public class ApplicationMemberNotFoundException extends RuntimeException{

    private ApplicationMessageCode applicationMessageCode;

    public ApplicationMemberNotFoundException(ApplicationMessageCode applicationMessageCode){
        this.applicationMessageCode = applicationMessageCode;
    }

}
