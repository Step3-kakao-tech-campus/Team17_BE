package com.kakaoseventeen.dogwalking.notification.domain;

import com.kakaoseventeen.dogwalking.dog.domain.Dog;
import com.kakaoseventeen.dogwalking.notification.dto.request.UpdateNotificationReqDTO;
import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "NOTIFICATION_tb")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTIFICATION_ID")
    private Long id;
    @ManyToOne
    private Dog dog;
    @OneToOne(mappedBy = "notification")
    private Walk walk;
    @Column(length = 256, nullable = false)
    private String title;
    @Column(length = 256, nullable = false)
    private Double lat;
    @Column(length = 256, nullable = false)
    private Double lng;
    @Column(nullable = false)
    private LocalDateTime startTime;
    @Column(nullable = false)
    private LocalDateTime endTime;
    @Column(length = 256, nullable = false)
    private String significant;
    @Column(nullable = false)
    private BigDecimal coin;

    @Builder
    public Notification(Dog dog, String title, Double lat, Double lng, LocalDateTime startTime, LocalDateTime endTime, String significant, BigDecimal coin){
        this.dog = dog;
        this.title = title;
        this.lat = lat;
        this.lng = lng;
        this.startTime = startTime;
        this.endTime = endTime;
        this.significant = significant;
        this.coin = coin;
    }

    public void update(UpdateNotificationReqDTO updateNotificationReqDTO, Dog dog){
        this.title = updateNotificationReqDTO.getTitle();
        this.dog = dog;
        this.lat = updateNotificationReqDTO.getLat();
        this.lng = updateNotificationReqDTO.getLng();
        this.startTime = updateNotificationReqDTO.getStart();
        this.endTime = updateNotificationReqDTO.getEnd();
        this.significant = updateNotificationReqDTO.getSignificant();
        this.coin = updateNotificationReqDTO.getCoin();
    }

}