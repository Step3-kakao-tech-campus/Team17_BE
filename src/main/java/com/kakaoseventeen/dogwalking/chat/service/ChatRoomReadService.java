package com.kakaoseventeen.dogwalking.chat.service;

import com.kakaoseventeen.dogwalking.chat.domain.ChatMessage;
import com.kakaoseventeen.dogwalking.chat.domain.ChatRoom;
import com.kakaoseventeen.dogwalking.chat.dto.ChatListResDTO;
import com.kakaoseventeen.dogwalking.chat.repository.ChatMessageRepository;
import com.kakaoseventeen.dogwalking.chat.repository.ChatRoomRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;
import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import com.kakaoseventeen.dogwalking.walk.repository.WalkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 박영규
 * @version 1.1
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomReadService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final WalkRepository walkRepository;

    private String getEmail(){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String email = loggedInUser.getName();
        return email;
    }

    /**
     * 채팅 방 목록조회
     *
     * @return List of ChatListResDTO
     */
    public List<ChatListResDTO> getChatList() {

        Member member = memberJpaRepository.findByEmail(getEmail())
                .orElseThrow(() -> new RuntimeException("인증되지 않았습니다."));

        List<ChatRoom> chatRooms = chatRoomRepository.findChatRoomsByAppMemberIdOrNotiMemberId(member, member);

        List<ChatListResDTO> chatListResDTOS = chatRooms.stream().map(chatRoom -> {

            ChatMessage chatMessage = chatMessageRepository.findTop1ByChatRoomIdOrderByChatMessageIdDesc(chatRoom)
                    .orElseThrow(() -> new RuntimeException("ChatMessage조회 에러"));

            Walk walk = walkRepository.findWalkByMaster(chatRoom.getNotiMemberId())
                    .orElseThrow(() -> new RuntimeException("산책의 정보가 존재하지 않습니다."));

            return ChatListResDTO.builder()
                    .chatRoomId(chatRoom.getChatRoomId())
                    .memberId(chatMessage.getSenderId().getId())
                    .memberNickname(chatMessage.getSenderId().getNickname())
                    .memberImage(chatMessage.getSenderId().getProfileImage())
                    .chatContent(chatMessage.getContent())
                    .walkType(walk.getWalkStatus().toString())
                    .matchId(chatRoom.getMatchId().getMatchId())
                    .build();
        }).toList();

        return chatListResDTOS;

    }
}
