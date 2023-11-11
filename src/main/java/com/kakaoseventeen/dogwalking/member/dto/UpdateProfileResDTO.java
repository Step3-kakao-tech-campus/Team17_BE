package com.kakaoseventeen.dogwalking.member.dto;

import com.kakaoseventeen.dogwalking.member.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateProfileResDTO {

    private String profileImage;

    private String profileContent;

    public UpdateProfileResDTO(Member member){
        this.profileContent = member.getProfileContent();
        this.profileImage = member.getProfileImage();
    }
}
