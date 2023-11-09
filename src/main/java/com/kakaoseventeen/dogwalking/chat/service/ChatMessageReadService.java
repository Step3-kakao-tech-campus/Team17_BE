package com.kakaoseventeen.dogwalking.chat.service;

import com.kakaoseventeen.dogwalking.chat.domain.ChatMessage;
import com.kakaoseventeen.dogwalking.chat.domain.ChatRoom;
import com.kakaoseventeen.dogwalking.chat.dto.ChatMessageResDTO;
import com.kakaoseventeen.dogwalking.chat.repository.ChatMessageRepository;
import com.kakaoseventeen.dogwalking.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatMessageReadService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    public List<ChatMessageResDTO> getMessage(Long chatRoomId){

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new RuntimeException("일치하는 채팅방이 존재하지 않습니다."));
        List<ChatMessage> chatMessages = chatMessageRepository.findChatMessageByChatRoomId(chatRoom);

        return chatMessages.stream()
                .map(chatMessage -> ChatMessageResDTO.builder()
                            .messageType(chatMessage.getMessageType())
                            .memberId(chatMessage.getSenderId().getId())
                            .content(chatMessage.getContent())
                            .build()
                ).toList();


    }

}
