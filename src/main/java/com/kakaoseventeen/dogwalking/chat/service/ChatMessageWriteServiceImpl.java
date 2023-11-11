package com.kakaoseventeen.dogwalking.chat.service;

import com.kakaoseventeen.dogwalking._core.utils.ChatMessageCode;
import com.kakaoseventeen.dogwalking._core.utils.exception.chatMessage.ChatRoomNotFoundException;
import com.kakaoseventeen.dogwalking._core.utils.exception.chatMessage.MemberIdNotFoundException;
import com.kakaoseventeen.dogwalking._core.utils.exception.chatMessage.NotFoundMemberInChatRoomException;
import com.kakaoseventeen.dogwalking.chat.dto.ChatReqDTO;
import com.kakaoseventeen.dogwalking.chat.dto.ChatResDTO;
import com.kakaoseventeen.dogwalking.chat.domain.ChatMessage;
import com.kakaoseventeen.dogwalking.chat.domain.ChatRoom;
import com.kakaoseventeen.dogwalking.chat.domain.MessageType;
import com.kakaoseventeen.dogwalking.chat.repository.ChatMessageRepository;
import com.kakaoseventeen.dogwalking.chat.repository.ChatRoomRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ChatMessageWriteServiceImpl implements ChatMessageWriteService {

    private final ChatMessageRepository chatMessageRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Override
    @Transactional
    public ChatResDTO save(ChatReqDTO chatReqDTO, Long roomId) {

        Member sender = memberJpaRepository.findById(chatReqDTO.memberId())
                .orElseThrow(() -> new MemberIdNotFoundException(ChatMessageCode.MEMBER_ID_NOT_FOUND));
        
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new ChatRoomNotFoundException(ChatMessageCode.CHATROOM_NOT_FOUND));

        validSender(sender, chatRoom);

        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoomId(chatRoom)
                .content(chatReqDTO.chatContent())
                .senderId(sender)
                .messageType(MessageType.valueOf(chatReqDTO.messageType().toString()))
                .build();

        chatMessageRepository.save(chatMessage);

        return ChatResDTO.builder()
                .chatId(chatMessage.getChatMessageId())
                .userId(chatReqDTO.memberId())
                .chatContent(chatMessage.getContent())
                .messageType(chatReqDTO.messageType())
                .contentTime(chatMessage.getCreatedAt())
                .build();
    }

    private void validSender(Member sender, ChatRoom chatRoom) {
        if(!Objects.equals(chatRoom.getNotiMemberId().getId(), sender.getId()) && !Objects.equals(chatRoom.getAppMemberId().getId(), sender.getId())){
            throw new NotFoundMemberInChatRoomException(ChatMessageCode.NOT_FOUND_MEMBER_IN_CHAT_ROOM);
        }


    }
}
