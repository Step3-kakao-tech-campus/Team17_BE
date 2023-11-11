package com.kakaoseventeen.dogwalking.member.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * IsOwnerResDTO : 본인 프로필인지 확인 응답 DTO
 *
 * @author 승건 이
 * @version 1.0
 */
@Getter
@Setter
public class IsOwnerResDTO {
    private boolean isOwner;

    public IsOwnerResDTO(boolean isOwner){
        this.isOwner = isOwner;
    }
}
