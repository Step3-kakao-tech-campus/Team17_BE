package com.kakaoseventeen.dogwalking.walk.domain;

import com.kakaoseventeen.dogwalking.dog.Dog;
import com.kakaoseventeen.dogwalking.member.domain.Member;
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
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Dog dog;

    @Enumerated(value = EnumType.STRING)
    private WalkStatus walkStatus;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public static Walk of(Dog dog, Member member){
        return Walk.builder()
                .startTime(LocalDateTime.now())
                .walkStatus(WalkStatus.ACTIVATE)
                .dog(dog)
                .member(member)
                .build();
    }

    /**
     * RoomEntity의 lastSendTime을 최신화 하는 메서드
     */
    public void updateStatus(WalkStatus walkStatus){
        this.walkStatus = walkStatus;
    }
}
