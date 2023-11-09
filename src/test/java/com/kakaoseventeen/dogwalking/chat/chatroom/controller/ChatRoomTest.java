package com.kakaoseventeen.dogwalking.chat.chatroom.controller;

import com.kakaoseventeen.dogwalking.application.domain.Application;
import com.kakaoseventeen.dogwalking.application.repository.ApplicationRepository;
import com.kakaoseventeen.dogwalking.chat.controller.ChatRoomController;
import com.kakaoseventeen.dogwalking.chat.domain.ChatMessage;
import com.kakaoseventeen.dogwalking.chat.domain.ChatRoom;
import com.kakaoseventeen.dogwalking.chat.domain.MessageType;
import com.kakaoseventeen.dogwalking.chat.dto.ChatRoomReqDTO;
import com.kakaoseventeen.dogwalking.chat.repository.ChatMessageRepository;
import com.kakaoseventeen.dogwalking.chat.repository.ChatRoomRepository;
import com.kakaoseventeen.dogwalking.dog.domain.Dog;
import com.kakaoseventeen.dogwalking.dog.repository.DogJpaRepository;
import com.kakaoseventeen.dogwalking.match.domain.Match;
import com.kakaoseventeen.dogwalking.match.repository.MatchingRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.notification.repository.NotificationJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class ChatRoomTest {


    @Autowired
    ChatRoomRepository chatRoomRepository;
    @Autowired
    MemberJpaRepository memberJpaRepository;


    @PersistenceContext
    private EntityManager em;
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private DogJpaRepository dogJpaRepository;
    @Autowired
    private NotificationJpaRepository notificationJpaRepository;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private MatchingRepository matchingRepository;
    @Autowired
    MockMvc mockMvc;


    @BeforeEach
    public void setUp(){
        log.info("setup 시작");
        Member member1 = Member.builder() // 공고자
                .id(1L)
                .nickname("test1")
                .email("test1@naver.com")
                .password("test1")
                .build();
        Member member2 = Member.builder() // 지원자
                .id(2L)
                .nickname("test2")
                .email("test2@naver.com")
                .password("test2")
                .build();
        memberJpaRepository.saveAll(List.of(member1, member2));

        Dog dog = Dog.builder()
                .name("테스트 개이름")
                .sex("수컷")
                .breed("testBreed")
                .size("testSize")
                .member(member1)
                .build();
        dogJpaRepository.save(dog);

        Notification notification = Notification.builder()
                .dog(dog)
                .title("notiTestTitle")
                .lat(22.0)
                .lng(22.3)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .significant("testSig")
                .coin(BigDecimal.ONE)
                .build();
        notificationJpaRepository.save(notification);

        Application application = Application.builder()
                .applicationId(1L)
                .title("지원서 제목")
                .aboutMe("나에 대해서")
                .appMemberId(member2)
                .build();
        applicationRepository.save(application);

        Match match = Match.builder()
                .matchId(1L)
                .notificationId(notification)
                .applicationId(application)
                .isSuccess(true)
                .createdAt(LocalDateTime.now())
                .build();
        matchingRepository.save(match);

        ChatRoom chatRoom1 = ChatRoom.builder()
                .notiMemberId(member1)
                .appMemberId(member2)
                .matchId(match)
                .build();

        chatRoomRepository.saveAndFlush(chatRoom1);

        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoomId(chatRoom1)
                .content("채팅 테스트1")
                .senderId(member1)
                .messageType(MessageType.CHAT)
                .build();
        chatMessageRepository.saveAndFlush(chatMessage);

        em.flush();
        em.clear();
        log.info("setup종료");
    }

    @DisplayName("채팅방 목록 조회 통합 테스트")
    @Test
    @WithMockUser(username = "test1@naver.com")
    public void ChatRoom_test() throws Exception{
        // given
        log.info("테스트 시작");

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/chat/list")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(
                MockMvcResultMatchers.jsonPath("$.success").value("true")
        );
        log.info("테스트 종료");
    }
}
