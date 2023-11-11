package com.kakaoseventeen.dogwalking.chat.service;

import com.kakaoseventeen.dogwalking._core.utils.ChatRoomMessageCode;
import com.kakaoseventeen.dogwalking._core.utils.exception.chatroom.ChatMessageNotFoundException;
import com.kakaoseventeen.dogwalking._core.utils.exception.chatroom.InvalidMemberException;
import com.kakaoseventeen.dogwalking._core.utils.exception.chatroom.WalkNotFoundException;
import com.kakaoseventeen.dogwalking.chat.domain.ChatMessage;
import com.kakaoseventeen.dogwalking.chat.domain.ChatRoom;
import com.kakaoseventeen.dogwalking.chat.dto.ChatListResDTO;
import com.kakaoseventeen.dogwalking.chat.repository.ChatMessageRepository;
import com.kakaoseventeen.dogwalking.chat.repository.ChatRoomRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberRepository;
import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import com.kakaoseventeen.dogwalking.walk.repository.WalkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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
    private final MemberRepository memberRepository;
    private final WalkRepository walkRepository;

    private String getEmail(){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        return loggedInUser.getName();
    }

    /**
     * 채팅 방 목록조회
     *
     * @return List of ChatListResDTO
     */
    public List<ChatListResDTO> getChatList() {

        Member member = memberRepository.findByEmail(getEmail())
                .orElseThrow(() -> new InvalidMemberException(ChatRoomMessageCode.INVALID_MEMBER));

        List<ChatRoom> chatRooms = chatRoomRepository.findChatRoomsByAppMemberIdOrNotiMemberId(member, member);

        List<ChatListResDTO> chatListResDTOS = chatRooms.stream().map(chatRoom -> {

            Walk walk = walkRepository.findWalkByMaster(chatRoom.getNotiMemberId())
                    .orElseThrow(() -> new WalkNotFoundException(ChatRoomMessageCode.WALK_NOT_FOUND));

            boolean isDogOwner = Objects.equals(walk.getMaster().getId(), member.getId());

            ChatMessage chatMessage = chatMessageRepository.findTop1ByChatRoomIdOrderByChatMessageIdDesc(chatRoom)
                    .orElseThrow(() -> new ChatMessageNotFoundException(ChatRoomMessageCode.CHAT_MESSAGE_NOT_FOUND));



            return ChatListResDTO.builder()
                    .chatRoomId(chatRoom.getChatRoomId())
                    .memberId(member.getId())
                    .memberNickname(chatMessage.getSenderId().getNickname())
                    .memberImage(chatMessage.getSenderId().getProfileImage())
                    .chatContent(chatMessage.getContent())
                    .walkType(walk.getWalkStatus().toString())
                    .matchId(chatRoom.getMatchId().getMatchId())
                    .isDogOwner(isDogOwner)
                    .build();
        }).toList();

        return chatListResDTOS;

    }
}
