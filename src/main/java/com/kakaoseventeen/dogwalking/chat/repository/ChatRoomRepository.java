package com.kakaoseventeen.dogwalking.chat.repository;

import com.kakaoseventeen.dogwalking.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
