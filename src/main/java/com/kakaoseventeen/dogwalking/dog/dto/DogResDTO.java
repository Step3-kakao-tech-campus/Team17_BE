package com.kakaoseventeen.dogwalking.dog.dto;

import com.kakaoseventeen.dogwalking.dog.domain.Dog;
import lombok.Getter;
import lombok.Setter;

/**
 * DogRespDTO : 프론트엔드의 Dog 프로필 조회 API 요청에 맞는 값들을 반환하는 DTO
 *
 * @author 승건 이
 * @version 1.0
 */
public class DogResDTO {

    @Getter
    @Setter
    public static class save {

        private Long dogId;

        private String image;

        private String name;

        private String sex;

        private String breed;

        private String size;

        private String specificity;

        private int age;

        private Long memberId;

        public save(Dog dog){
            this.dogId = dog.getId();
            this.image = dog.getImage();
            this.name = dog.getName();
            this.sex = dog.getSex();
            this.breed = dog.getBreed();
            this.size = dog.getSize();
            this.specificity = dog.getSpecificity();
            this.age = dog.getAge();
            this.memberId = dog.getMember().getId();
        }
    }

    @Getter
    @Setter
    public static class findById {

        private Long dogId;

        private String image;

        private String name;

        private String sex;

        private String breed;

        private String size;

        private String specificity;

        private int age;

        private Long memberId;

        public findById(Dog dog){
            this.dogId = dog.getId();
            this.image = dog.getImage();
            this.name = dog.getName();
            this.sex = dog.getSex();
            this.breed = dog.getBreed();
            this.size = dog.getSize();
            this.specificity = dog.getSpecificity();
            this.age = dog.getAge();
            this.memberId = dog.getMember().getId();
        }
    }
}
