package com.kakaoseventeen.dogwalking.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IsOwnerResDTO {
    private boolean isOwner;

    public IsOwnerResDTO(boolean isOwner){
        this.isOwner = isOwner;
    }
}
