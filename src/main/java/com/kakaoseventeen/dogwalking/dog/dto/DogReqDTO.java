package com.kakaoseventeen.dogwalking.dog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class DogReqDTO {

    private String image;

    private String name;

    private String sex;

    private String breed;

    private String size;

    private String specificity;

    private int age;

}
