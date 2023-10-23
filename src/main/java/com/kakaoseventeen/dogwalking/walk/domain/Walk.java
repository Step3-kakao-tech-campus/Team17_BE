package com.kakaoseventeen.dogwalking.walk.domain;

import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Walk(산책) 엔티티
 *
 * @author 승건 이
 * @version 1.0
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Walk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member walker;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member master;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NOTIFICATION_ID")
    private Notification notification;

    @Enumerated(value = EnumType.STRING)
    private WalkStatus walkStatus;

    private LocalDateTime startTime;

    private LocalDateTime endTime;


    /**
     * Walk Entity 생성, 즉 산책 허락하기시 객체 생성 메서드
     */
    public static Walk of(Member walker, Member master, Notification notification){
        return Walk.builder()
                .walkStatus(WalkStatus.READY)
                .master(master)
                .walker(walker)
                .notification(notification)
                .build();
    }

    /**
     * 산책 시작하는 메서드
     */
    public void startWalk(){
        this.startTime = LocalDateTime.now();
    }

    /**
     * 산책 활성화하는 메서드
     */
    public void activateWalk(){
        this.walkStatus = WalkStatus.ACTIVATE;
    }

    /**
     * 산책 종료하는 메서드
     */
    public void endWalk(){
        this.walkStatus = WalkStatus.END;
        this.endTime = LocalDateTime.now();
    }
}
