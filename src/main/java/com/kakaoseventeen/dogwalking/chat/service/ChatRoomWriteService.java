package com.kakaoseventeen.dogwalking.chat.service;

import com.kakaoseventeen.dogwalking._core.utils.ChatRoomMessageCode;
import com.kakaoseventeen.dogwalking._core.utils.exception.chatroom.ChatRoomMatchNotFoundException;
import com.kakaoseventeen.dogwalking._core.utils.exception.chatroom.ChatRoomMemberNotFoundException;
import com.kakaoseventeen.dogwalking.chat.domain.ChatRoom;
import com.kakaoseventeen.dogwalking.chat.dto.ChatRoomReqDTO;
import com.kakaoseventeen.dogwalking.chat.repository.ChatRoomRepository;
import com.kakaoseventeen.dogwalking.match.domain.Match;
import com.kakaoseventeen.dogwalking.match.repository.MatchingRepository;
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
    private final MatchingRepository matchingRepository;

    @Transactional
    public void save(ChatRoomReqDTO chatRoomReqDTO){

        Member appMember = memberJpaRepository.findById(chatRoomReqDTO.appMemberId())
                .orElseThrow(() -> new ChatRoomMemberNotFoundException(ChatRoomMessageCode.MEMBER_NOT_FOUND));
        Member notiMember = memberJpaRepository.findById(chatRoomReqDTO.notiMemberId())
                .orElseThrow(() -> new ChatRoomMemberNotFoundException(ChatRoomMessageCode.MEMBER_NOT_FOUND));
        Match match = matchingRepository.findById(chatRoomReqDTO.matchId())
                .orElseThrow(() ->new ChatRoomMatchNotFoundException(ChatRoomMessageCode.MATCH_NOT_FOUND));

        match.updateIsSuccess(true);
        ChatRoom chatRoom = getChatRoom(appMember, notiMember, match);

        chatRoomRepository.save(chatRoom);
    }

    private static ChatRoom getChatRoom(Member appMember, Member notiMember, Match match) {
        ChatRoom chatRoom = ChatRoom.builder()
                .appMemberId(appMember)
                .notiMemberId(notiMember)
                .matchId(match)
                .build();
        return chatRoom;
    }


}
