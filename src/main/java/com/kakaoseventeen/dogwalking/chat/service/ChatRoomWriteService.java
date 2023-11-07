package com.kakaoseventeen.dogwalking.chat.service;

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

        // TODO - 커스텀 Exception 적용할 것
        Member appMember = memberJpaRepository.findById(chatRoomReqDTO.appMemberId()).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Member notiMember = memberJpaRepository.findById(chatRoomReqDTO.notiMemberId()).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

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
