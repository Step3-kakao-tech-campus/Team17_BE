package com.kakaoseventeen.dogwalking.chat.chatroom.service;

import com.kakaoseventeen.dogwalking.chat.dto.ChatRoomReqDTO;
import com.kakaoseventeen.dogwalking.chat.service.ChatRoomWriteService;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


/**
 * ChatRoomWriteServiceTest(채팅 방 서비스 연결 테스트)
 * ChatRoomWriteService 와 Repository 연결 테스트
 *
 * @author 영규 박
 * @version 1.0
 */
@SpringBootTest
public class ChatRoomWriteServiceTest {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Autowired
    private ChatRoomWriteService chatRoomWriteService;

    @BeforeEach
    public void setUp() {
        // member
        Member member1 = Member.builder() // noti
                .id(1L)
                .nickname("테스트1")
                .email("test1@naver.com")
                .password("test1")
                .build();
        Member member2 = Member.builder() // app
                .id(2L)
                .nickname("테스트2")
                .email("test2@naver.com")
                .password("test2")
                .build();
        memberJpaRepository.saveAll(List.of(member1, member2));

        em.clear();
    }

    @DisplayName("채팅방 생성 연결 테스트")
    @Test
    public void save_test() {
        // given
        ChatRoomReqDTO chatRoomReqDTO = ChatRoomReqDTO.builder()
                .notiMemberId(1L)
                .appMemberId(2L)
                .build();

        // when
        chatRoomWriteService.save(chatRoomReqDTO);

        // then
    }



}
