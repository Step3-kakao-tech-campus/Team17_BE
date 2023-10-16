package com.kakaoseventeen.dogwalking._core.payment.domain;

import com.kakaoseventeen.dogwalking._core.walk.domain.Walk;
import jakarta.persistence.*;
import lombok.*;

/**
 * Payment(결제) 엔티티
 *
 * @author 승건 이
 * @version 1.0
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Walk walk;

    public static Payment from(Walk walk){
        return Payment.builder()
                .walk(walk)
                .build();
    }
}