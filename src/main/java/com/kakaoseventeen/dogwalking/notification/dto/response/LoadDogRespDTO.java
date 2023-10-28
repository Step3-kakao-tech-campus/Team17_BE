package com.kakaoseventeen.dogwalking.notification.dto.response;

import com.kakaoseventeen.dogwalking.dog.domain.Dog;
import lombok.Getter;

import java.util.List;

@Getter
public class LoadDogRespDTO {

    private List<DogDTO> dogs;

    public LoadDogRespDTO(List<Dog> dogList){
        this.dogs = dogList.stream().map(DogDTO::new).toList();
    }

    @Getter
    public class DogDTO{
        private Long dogId;
        private String dogImage;
        private String dogName;


        public DogDTO(Dog dog){
            this.dogId = dog.getId();
            this.dogImage = dog.getImage();
            this.dogName = dog.getName();
        }
    }
}