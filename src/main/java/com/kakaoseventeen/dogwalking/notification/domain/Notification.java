package com.kakaoseventeen.dogwalking.notification.domain;

import com.kakaoseventeen.dogwalking.dog.Dog;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="notification_tb")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private Dog dog;
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

}