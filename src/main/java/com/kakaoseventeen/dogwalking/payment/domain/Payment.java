package com.kakaoseventeen.dogwalking.payment.domain;

import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

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

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    private Walk walk;

    private BigDecimal tmpMoney;

    public static Payment of(Walk walk, BigDecimal tmpMoney){
        return Payment.builder()
                .walk(walk)
                .tmpMoney(tmpMoney)
                .build();
    }
}