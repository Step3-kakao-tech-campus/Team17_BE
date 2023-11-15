package com.kakaoseventeen.dogwalking.chat.service;
import com.kakaoseventeen.dogwalking._core.utils.ChatRoomMessageCode;
import com.kakaoseventeen.dogwalking._core.utils.exception.chatroom.InvalidMemberException;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
/**
 * @author 박영규
 * @version 1.1
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
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

        log.info("로그인 멤버 id : {}", member.getId());

        List<ChatRoom> chatRooms = chatRoomRepository.findChatRoomsByAppMemberIdOrNotiMemberId(member, member);

        log.info("채팅방 개수 : {}", chatRooms.size());

        List<ChatListResDTO> chatListResDTOS = chatRooms.stream().map(chatRoom -> {
            String walkType = null;

            Optional<Walk> walk = walkRepository.findWalkByNotification(chatRoom.getMatchId().getNotificationId());
            log.info("산책이 있는가?: {}",walk.isPresent());

            if(walk.isPresent()){
                walkType = walk.get().getWalkStatus().toString();
            }

            Long applicationMemberId = chatRoomRepository.mfindById(chatRoom.getChatRoomId())
                    .get()
                    .getMatchId()
                    .getApplicationId()
                    .getAppMemberId()
                    .getId();

            log.info("조회한 지원자 ID : {}",applicationMemberId);

            boolean isDogOwner = !Objects.equals(applicationMemberId, member.getId());

            String memberNickname = null;
            String memberImage = null;

            if(isDogOwner){
                memberNickname=chatRoom.getAppMemberId().getNickname();
                memberImage=chatRoom.getAppMemberId().getProfileImage();
            }else {
                memberNickname=chatRoom.getNotiMemberId().getNickname();
                memberImage=chatRoom.getNotiMemberId().getProfileImage();
            }

            // 채팅내역
            Optional<ChatMessage> chatMessage = chatMessageRepository.findTop1ByChatRoomIdOrderByChatMessageIdDesc(chatRoom);
            String chatContent = "채팅 내역이 존재하지 않습니다.";

            if(chatMessage.isPresent()){
                chatContent = chatMessage.get().getContent();
            }


            return ChatListResDTO.builder()
                    .chatRoomId(chatRoom.getChatRoomId())
                    .memberId(member.getId())
                    .memberNickname(memberNickname)
                    .memberImage(memberImage)
                    .chatContent(chatContent)
                    .walkType(walkType)
                    .matchId(chatRoom.getMatchId().getMatchId())
                    .isDogOwner(isDogOwner)
                    .build();
        }).toList();

        return chatListResDTOS;
    }
}