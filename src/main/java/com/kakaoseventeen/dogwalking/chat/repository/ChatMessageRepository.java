package com.kakaoseventeen.dogwalking.chat.repository;

import com.kakaoseventeen.dogwalking.chat.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
