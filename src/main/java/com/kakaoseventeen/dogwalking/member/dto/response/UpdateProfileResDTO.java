package com.kakaoseventeen.dogwalking.member.dto.response;

import com.kakaoseventeen.dogwalking.member.domain.Member;
import lombok.Getter;
import lombok.Setter;

/**
 * UpdateProfileResDTO : 프로필 수정 응답 DTO
 *
 * @author 승건 이
 * @version 1.0
 */
@Getter @Setter
public class UpdateProfileResDTO {

    private String profileImage;

    private String profileContent;

    public UpdateProfileResDTO(Member member){
        this.profileContent = member.getProfileContent();
        this.profileImage = member.getProfileImage();
    }
}
