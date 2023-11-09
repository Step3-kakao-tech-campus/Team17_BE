package com.kakaoseventeen.dogwalking.chat.domain;

import com.kakaoseventeen.dogwalking.match.domain.Match;
import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CHAT_ROOM_tb")
@EntityListeners(AuditingEntityListener.class)
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;
    @OneToOne(fetch = FetchType.LAZY)
    private Member notiMemberId;
    @OneToOne(fetch = FetchType.LAZY)
    private Member appMemberId;

    @CreatedDate
    private LocalDateTime createdAt;
    @OneToOne(fetch = FetchType.LAZY)
    private Match matchId;

    @Builder
    public ChatRoom(Member notiMemberId, Member appMemberId, Match matchId){
        this.notiMemberId = notiMemberId;
        this.appMemberId = appMemberId;
        this.matchId=matchId;
    }
}
