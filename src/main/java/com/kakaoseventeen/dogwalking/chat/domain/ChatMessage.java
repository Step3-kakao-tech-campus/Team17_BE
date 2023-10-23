package com.kakaoseventeen.dogwalking.chat.domain;

import com.kakaoseventeen.dogwalking.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CHAT_MESSAGE_tb")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatMessageId;

    private String content;

    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoom chatRoomId;

    @OneToOne(fetch = FetchType.LAZY)
    private Member senderId;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public ChatMessage(String content, MessageType messageType, ChatRoom chatRoomId, Member senderId){
        this.content = content;
        this.messageType = messageType;
        this.chatRoomId = chatRoomId;
        this.senderId = senderId;
    }

}
