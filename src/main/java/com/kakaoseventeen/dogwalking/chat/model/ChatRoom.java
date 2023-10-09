package com.kakaoseventeen.dogwalking.chat.model;

import com.kakaoseventeen.dogwalking.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CHAT_ROOM")
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

    @Builder
    public ChatRoom(Member notiMemberId, Member appMemberId){
        this.notiMemberId = notiMemberId;
        this.appMemberId = appMemberId;
    }
}
