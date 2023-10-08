package com.kakaoseventeen.dogwalking.notification.dto;

import com.kakaoseventeen.dogwalking.Dog;
import lombok.Getter;

import java.util.List;

@Getter
public class LoadDogResponseDTO {

    private List<DogDTO> dogs;

    public LoadDogResponseDTO(List<Dog> dogList){
        for(Dog dog : dogList){
            this.dogs.add(new DogDTO(dog));
        }
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