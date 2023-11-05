package com.kakaoseventeen.dogwalking.dog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

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
