package com.kakaoseventeen.dogwalking.dog.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;


/**
 * DogReqDTO : 프론트엔드의 Dog 프로필 등록 API 요청을 받는 DTO
 *
 * @author 승건 이
 * @version 1.0
 */
@NoArgsConstructor
@Getter @Setter
public class DogReqDTO implements Serializable {

    private String name;

    private String sex;

    private String breed;

    private String size;

    private String specificity;

    private String age;

    private MultipartFile image;

}
