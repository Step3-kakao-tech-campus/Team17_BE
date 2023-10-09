package com.kakaoseventeen.dogwalking.walkRoad.domain;

import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import jakarta.persistence.*;
import lombok.*;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Walk walk;

    // 위도
    private double lat;

    // 경도
    private double lng;

    public static WalkRoad of(double lat, double lng){
        return WalkRoad.builder()
                .lat(lat)
                .lng(lng)
                .build();
    }
}
