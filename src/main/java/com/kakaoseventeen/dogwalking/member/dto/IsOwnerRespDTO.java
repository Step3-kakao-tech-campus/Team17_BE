package com.kakaoseventeen.dogwalking.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IsOwnerRespDTO {
    private boolean isOwner;

    public IsOwnerRespDTO(boolean isOwner){
        this.isOwner = isOwner;
    }
}
