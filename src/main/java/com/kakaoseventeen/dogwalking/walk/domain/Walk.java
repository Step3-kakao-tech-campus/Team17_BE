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
    private Member walker;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member master;

    @ManyToOne(fetch = FetchType.LAZY)
    private Dog dog;

/*    @OneToOne(fetch = FetchType.LAZY)
    private ChatRoom chatRoom;
    */

    @Enumerated(value = EnumType.STRING)
    private WalkStatus walkStatus;

    private LocalDateTime startTime;

    private LocalDateTime endTime;


    /**
     * Walk Entity 생성, 즉 산책 허락하기시 객체 생성 메서드
     */
    public static Walk of(Dog dog, Member walker, Member master){
        return Walk.builder()
                .walkStatus(WalkStatus.READY)
                .dog(dog)
                .master(master)
                .walker(walker)
                .build();
    }

    /**
     * 산책 시작하는 메서드
     */
    public void startWalk(){
        this.walkStatus = WalkStatus.ACTIVATE;
        this.startTime = LocalDateTime.now();
    }

    /**
     * 산책 종료하는 메서드
     */
    public void endWalk(){
        this.walkStatus = WalkStatus.END;
        this.endTime = LocalDateTime.now();
    }
}
