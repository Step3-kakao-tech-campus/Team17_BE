package com.kakaoseventeen.dogwalking.chat.model;

import com.kakaoseventeen.dogwalking.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CHAT_MESSAGE")
public class ChatMessage {
    @Id
    private Long chatMessageId;

    private String content;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoom chatRoomId;

    @OneToOne(fetch = FetchType.LAZY)
    private Member senderId;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public ChatMessage(String content, ContentType contentType, ChatRoom chatRoomId, Member senderId){
        this.content = content;
        this.contentType = contentType;
        this.chatRoomId = chatRoomId;
        this.senderId = senderId;
    }

}
