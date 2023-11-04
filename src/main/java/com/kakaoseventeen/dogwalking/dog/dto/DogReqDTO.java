package com.kakaoseventeen.dogwalking.dog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter @Builder
public class DogReqDTO {

    private String name;

    private String sex;

    private String breed;

    private String size;

    private String specificity;

    private int age;

}
