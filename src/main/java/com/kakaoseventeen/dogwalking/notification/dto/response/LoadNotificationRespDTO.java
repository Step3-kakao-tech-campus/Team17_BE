package com.kakaoseventeen.dogwalking.notification.dto.response;

import com.kakaoseventeen.dogwalking.dog.domain.Dog;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class LoadNotificationRespDTO {
    private Boolean isMine;
    private Long notificationId;
    private String title;
    private DogDTO dog;
    private Double lat;
    private Double lng;
    private LocalDateTime start;
    private LocalDateTime end;
    private BigDecimal coin;
    private String significant;

    public LoadNotificationRespDTO(Notification notification, Dog dog, Boolean isMine){
        this.isMine = isMine;
        this.notificationId = notification.getId();
        this.title = notification.getTitle();
        this.dog = new DogDTO(dog);
        this.lat = notification.getLat();
        this.lng = notification.getLng();
        this.start = notification.getStartTime();
        this.end = notification.getEndTime();
        this.coin = notification.getCoin();
        this.significant = notification.getSignificant();
    }

    @Getter
    public static class DogDTO {
        private Long dogId;
        private String name;
        private String breed;
        private String image;
        private String size;

        public DogDTO(Dog dog){
            this.dogId = dog.getId();
            this.name = dog.getName();
            this.breed = dog.getBreed();
            this.image = dog.getImage();
            this.size = dog.getSize();
        }
    }
}