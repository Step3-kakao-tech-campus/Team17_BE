package com.kakaoseventeen.dogwalking.member.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Getter @Setter
public class UpdateProfileReqDTO implements Serializable {

    private String profileContent;

    private MultipartFile profileImage;
}
