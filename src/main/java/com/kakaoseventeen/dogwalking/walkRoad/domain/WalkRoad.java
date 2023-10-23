package com.kakaoseventeen.dogwalking.walkRoad.domain;

import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * WalkRoad(산책 경로) 엔티티
 *
 * @author 승건 이
 * @version 1.0
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class WalkRoad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Walk walk;

    // 위도
    private double lat;

    // 경도
    private double lng;

    @CreationTimestamp
    @Column(nullable = false, length = 20, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(length = 20)
    private LocalDateTime updatedAt;

    public static WalkRoad of(double lat, double lng, Walk walk){
        return WalkRoad.builder()
                .lat(lat)
                .lng(lng)
                .walk(walk)
                .build();
    }
}
