package com.kakaoseventeen.dogwalking.member.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter @Setter
public class UpdateProfileReqDTO implements Serializable {

    private String profileContent;

    private MultipartFile profileImage;
}
