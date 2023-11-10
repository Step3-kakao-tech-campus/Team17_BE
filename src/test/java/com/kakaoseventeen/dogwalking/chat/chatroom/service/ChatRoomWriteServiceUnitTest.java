package com.kakaoseventeen.dogwalking.chat.chatroom.service;

import com.kakaoseventeen.dogwalking.chat.domain.ChatRoom;
import com.kakaoseventeen.dogwalking.chat.dto.ChatRoomReqDTO;
import com.kakaoseventeen.dogwalking.chat.repository.ChatRoomRepository;
import com.kakaoseventeen.dogwalking.chat.service.ChatRoomWriteService;
import com.kakaoseventeen.dogwalking.match.repository.MatchingRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

/**
 * ChatRoomWriteServiceUnitTest(채팅 방 서비스 단위 테스트)
 *
 * @author 영규 박
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
public class ChatRoomWriteServiceUnitTest {

    @Mock
    MemberJpaRepository memberJpaRepository;

    @Mock
    ChatRoomRepository chatRoomRepository;

    @Mock
    MatchingRepository matchingRepository;

    @BeforeEach
    void setUp(){

    }

    @DisplayName("채팅방 생성 save() 단위 테스트")
    @Test
    public void save_test() {

        // given
        Long notiMemberId = 1L;
        Long appMemberId = 2L;
        Member appMember = Member.builder()
                .id(1L)
                .email("appMember@naver.com")
                .nickname("cookieApp")
                .password("test")
                .build();
        Member notiMember = Member.builder()
                .id(2L)
                .email("notiMember@naver.com")
                .nickname("cookieNoti")
                .password("test")
                .build();
        ChatRoomReqDTO chatRoomReqDTO =
                ChatRoomReqDTO.builder()
                        .appMemberId(appMemberId)
                        .notiMemberId(notiMemberId)
                        .build();
        ChatRoom chatRoom = ChatRoom.builder()
                .appMemberId(appMember)
                .notiMemberId(notiMember)
                .build();


        // when
        Mockito.when(memberJpaRepository.findById(eq(appMemberId))).thenReturn(Optional.of(appMember));
        Mockito.when(memberJpaRepository.findById(eq(notiMemberId))).thenReturn(Optional.of(notiMember));
        Mockito.when(chatRoomRepository.save(any(ChatRoom.class))).thenReturn(chatRoom);
        ChatRoomWriteService chatRoomWriteService = new ChatRoomWriteService(chatRoomRepository, memberJpaRepository, matchingRepository);
        chatRoomWriteService.save(chatRoomReqDTO);

        // then
    }
}
