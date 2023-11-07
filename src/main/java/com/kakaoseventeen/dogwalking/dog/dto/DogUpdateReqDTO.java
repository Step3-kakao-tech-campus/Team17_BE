package com.kakaoseventeen.dogwalking.dog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * DogUpdateReqDTO : 프론트엔드의 Dog 프로필 수정 API 요청을 받는 DTO
 *
 * @author 승건 이
 * @version 1.0
 */
@NoArgsConstructor
@Getter @Setter
public class DogUpdateReqDTO implements Serializable {

    private String name;

    private String sex;

    private String breed;

    private String size;

    private String specificity;

    private int age;

    private MultipartFile image;

}
