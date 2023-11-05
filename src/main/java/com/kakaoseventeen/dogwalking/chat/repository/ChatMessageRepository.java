package com.kakaoseventeen.dogwalking.chat.repository;

import com.kakaoseventeen.dogwalking.chat.domain.ChatMessage;
import com.kakaoseventeen.dogwalking.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    /**
     * 채팅 메세지 중 가장 최근에 보낸 채팅메세지 하나를 반환한다
     * @param chatRoomId
     * @return Optional ChatMessage
     */
    Optional<ChatMessage> findTop1ByChatRoomIdOrderByChatMessageIdDesc(ChatRoom chatRoomId);
}
