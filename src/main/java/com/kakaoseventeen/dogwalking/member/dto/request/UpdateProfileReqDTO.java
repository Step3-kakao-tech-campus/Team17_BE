package com.kakaoseventeen.dogwalking.member.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * UpdateProfileReqDTO : 프로필 수정 요청 DTO
 *
 * @author 승건 이
 * @version 1.0
 */
@NoArgsConstructor
@Getter @Setter
public class UpdateProfileReqDTO implements Serializable {

    private String profileContent;

    private MultipartFile profileImage;
}
