package com.kakaoseventeen.dogwalking.dog.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
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
