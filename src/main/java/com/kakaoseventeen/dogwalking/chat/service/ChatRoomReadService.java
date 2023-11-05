package com.kakaoseventeen.dogwalking.chat.service;

import com.kakaoseventeen.dogwalking.chat.domain.ChatMessage;
import com.kakaoseventeen.dogwalking.chat.domain.ChatRoom;
import com.kakaoseventeen.dogwalking.chat.dto.ChatListResDTO;
import com.kakaoseventeen.dogwalking.chat.repository.ChatMessageRepository;
import com.kakaoseventeen.dogwalking.chat.repository.ChatRoomRepository;
import com.kakaoseventeen.dogwalking.match.repository.MatchingRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;
import com.kakaoseventeen.dogwalking.notification.repository.NotificationJpaRepository;
import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import com.kakaoseventeen.dogwalking.walk.repository.WalkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomReadService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final WalkRepository walkRepository;
    private final MatchingRepository matchingRepository;
    private final NotificationJpaRepository notificationJpaRepository;

    private String getEmail(){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String email = loggedInUser.getName();
        return email;
    }

    public List<ChatListResDTO> getChatList() {
        // userID가 맞는지 검사
        Member member = memberJpaRepository.findByEmail(getEmail()).orElseThrow(() -> new RuntimeException("인증되지 않았습니다."));


        // ChatRoom 전부 조회
        List<ChatRoom> chatRooms = chatRoomRepository.findChatRoomsByAppMemberIdOrNotiMemberId(member, member);

        List<ChatListResDTO> chatListResDTOS = chatRooms.stream().map(chatRoom -> {
            // 하나씩 뺀다 -> chatRoomId를 뺀다.
            Long chatRoomId = chatRoom.getChatRoomId();
            // ChatMessage에서 조회(userId, userImage, chatContent)
            ChatMessage chatMessage = chatMessageRepository.findTop1ByChatRoomIdOrderByChatMessageIdDesc(chatRoom).orElseThrow(() -> new RuntimeException("ChatMessage조회 에러"));
            // NotificationId를 통해 Notification찾기

            // 채팅룸의 notiMember정보로 match, walk에서 찾기
            Walk walk = walkRepository.findWalkByMaster(chatRoom.getNotiMemberId()).orElseThrow(() -> new RuntimeException("산책의 정보가 존재하지 않습니다."));
            //Notification notification = notificationJpaRepository.findNotificationByMemberId()
            //Match match = matchingRepository.findMatchByNotificationId(chatRoom.getNotiMemberId());

            return ChatListResDTO.builder()
                    .chatRoomId(chatRoomId)
                    .userId(chatMessage.getSenderId().getId())
                    .userImage(chatMessage.getSenderId().getProfileImage())
                    .chatContent(chatMessage.getContent())
                    .walkType(walk.getWalkStatus().toString())
                    .build();
        }).toList();

        // TODO - MatchingId 포함

        return chatListResDTOS;









    }
}
