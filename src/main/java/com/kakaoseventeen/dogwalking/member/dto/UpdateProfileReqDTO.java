package com.kakaoseventeen.dogwalking.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateProfileReqDTO {

    private String profileImage;

    private String profileContent;
}
