package com.kakaoseventeen.dogwalking.chat.service;

import com.kakaoseventeen.dogwalking._core.utils.exception.ChatRoomMessageCode;
import com.kakaoseventeen.dogwalking._core.utils.exception.chatroom.ChatRoomMemberNotFoundException;
import com.kakaoseventeen.dogwalking.chat.domain.ChatRoom;
import com.kakaoseventeen.dogwalking.chat.dto.ChatRoomReqDTO;
import com.kakaoseventeen.dogwalking.chat.repository.ChatRoomRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatRoomWriteService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberJpaRepository memberJpaRepository;

    @Transactional
    public void save(ChatRoomReqDTO chatRoomReqDTO){

        Member appMember = memberJpaRepository.findById(chatRoomReqDTO.appMemberId()).orElseThrow(() -> new ChatRoomMemberNotFoundException(ChatRoomMessageCode.MEMBER_NOT_FOUND));
        Member notiMember = memberJpaRepository.findById(chatRoomReqDTO.notiMemberId()).orElseThrow(() -> new ChatRoomMemberNotFoundException(ChatRoomMessageCode.MEMBER_NOT_FOUND));

        ChatRoom chatRoom = getChatRoom(appMember, notiMember);

        chatRoomRepository.save(chatRoom);
    }

    private static ChatRoom getChatRoom(Member appMember, Member notiMember) {
        ChatRoom chatRoom = ChatRoom.builder()
                .appMemberId(appMember)
                .notiMemberId(notiMember)
                .build();
        return chatRoom;
    }


}
