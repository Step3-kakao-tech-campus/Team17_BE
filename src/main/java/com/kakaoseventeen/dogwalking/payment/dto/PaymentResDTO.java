package com.kakaoseventeen.dogwalking.payment.dto;

import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * PaymentResDTO(결제 정보 조회 응답) DTO
 *
 * @author 승건 이
 * @version 1.0
 */
@Getter
@Setter
@Builder
public class PaymentResDTO {

    private Long userId;
    private String profile;

    private Long walkId;
    private LocalDateTime walkStartTIme;
    private LocalDateTime walkEndTime;

    private Long notificationId;
    private BigDecimal coin;

    public static PaymentResDTO of(Member member, Walk walk, Notification notification){
        return PaymentResDTO.builder()
                .userId(member.getId())
                .profile(member.getProfileImage())
                .walkId(walk.getId())
                .walkStartTIme(walk.getStartTime())
                .walkEndTime(walk.getEndTime())
                .notificationId(notification.getId())
                .coin(notification.getCoin())
                .build();
    }
}
