package com.kakaoseventeen.dogwalking.dog.dto;

import com.kakaoseventeen.dogwalking.dog.domain.Dog;
import lombok.Getter;
import lombok.Setter;

public class DogRespDTO {

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
