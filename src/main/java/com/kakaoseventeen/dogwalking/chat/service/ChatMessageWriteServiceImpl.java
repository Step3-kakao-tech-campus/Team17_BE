package com.kakaoseventeen.dogwalking.chat.service;

import com.kakaoseventeen.dogwalking.chat.dto.ChatReqDTO;
import com.kakaoseventeen.dogwalking.chat.dto.ChatResDTO;
import com.kakaoseventeen.dogwalking.chat.model.ChatMessage;
import com.kakaoseventeen.dogwalking.chat.model.ChatRoom;
import com.kakaoseventeen.dogwalking.chat.model.MessageType;
import com.kakaoseventeen.dogwalking.chat.repository.ChatMessageRepository;
import com.kakaoseventeen.dogwalking.chat.repository.ChatRoomRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatMessageWriteServiceImpl implements ChatMessageWriteService {

    private final ChatMessageRepository chatMessageRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Override
    @Transactional
    public ChatResDTO save(ChatReqDTO chatReqDTO, Long roomId) {

        // TODO - 예외처리 수정할 것
        Member sender = memberJpaRepository.findById(chatReqDTO.memberId()).orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("해당 채팅방이 존재하지 않습니다."));

        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoomId(chatRoom)
                .content(chatReqDTO.chatContent())
                .senderId(sender)
                .messageType(MessageType.valueOf(chatReqDTO.messageType().toString()))
                .build();
        // TODO - 인증과정 추가할 것
        chatMessageRepository.save(chatMessage);

        return ChatResDTO.builder()
                .chatId(chatMessage.getChatMessageId())
                .userId(chatReqDTO.memberId())
                .chatContent(chatMessage.getContent())
                .messageType(chatReqDTO.messageType())
                .contentTime(chatMessage.getCreatedAt())
                .build();
    }
}
