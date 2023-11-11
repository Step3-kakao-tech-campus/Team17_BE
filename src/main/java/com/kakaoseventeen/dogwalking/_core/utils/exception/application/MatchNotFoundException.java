package com.kakaoseventeen.dogwalking._core.utils.exception.application;

import com.kakaoseventeen.dogwalking._core.utils.ApplicationMessageCode;
import lombok.Getter;

@Getter
public class MatchNotFoundException extends IllegalArgumentException{
    private ApplicationMessageCode applicationMessageCode;

    public MatchNotFoundException(ApplicationMessageCode applicationMessageCode){
        this.applicationMessageCode=applicationMessageCode;
    }
}
