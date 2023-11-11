package com.kakaoseventeen.dogwalking.chat.chatroom.service;

import com.kakaoseventeen.dogwalking.chat.domain.ChatRoom;
import com.kakaoseventeen.dogwalking.chat.dto.ChatRoomReqDTO;
import com.kakaoseventeen.dogwalking.chat.repository.ChatRoomRepository;
import com.kakaoseventeen.dogwalking.chat.service.ChatRoomWriteService;
import com.kakaoseventeen.dogwalking.match.domain.Match;
import com.kakaoseventeen.dogwalking.match.repository.MatchRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberRepository;
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
    MemberRepository memberRepository;

    @Mock
    ChatRoomRepository chatRoomRepository;

    @Mock
    MatchRepository matchRepository;

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
                        .matchId(1L)
                        .build();
        Match match = Match.builder()
                .matchId(1L)
                .build();
        ChatRoom chatRoom = ChatRoom.builder()
                .appMemberId(appMember)
                .notiMemberId(notiMember)
                .matchId(match)
                .build();


        // when
        Mockito.when(memberRepository.findById(eq(appMemberId))).thenReturn(Optional.of(appMember));
        Mockito.when(memberRepository.findById(eq(notiMemberId))).thenReturn(Optional.of(notiMember));
        Mockito.when(chatRoomRepository.save(any(ChatRoom.class))).thenReturn(chatRoom);
        Mockito.when(matchRepository.findById(any(Long.class))).thenReturn(Optional.of(match));
        ChatRoomWriteService chatRoomWriteService = new ChatRoomWriteService(chatRoomRepository, memberRepository, matchRepository);

        chatRoomWriteService.save(chatRoomReqDTO);

        // then
    }
}
