package com.kakaoseventeen.dogwalking.dog.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Getter @Setter
public class DogReqDTO implements Serializable {

    private String name;

    private String sex;

    private String breed;

    private String size;

    private String specificity;

    private int age;

    private MultipartFile image;

}
