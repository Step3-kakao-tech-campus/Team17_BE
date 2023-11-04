package com.kakaoseventeen.dogwalking.member.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class UpdateProfileReqDTO {
    private String profileContent;
}
