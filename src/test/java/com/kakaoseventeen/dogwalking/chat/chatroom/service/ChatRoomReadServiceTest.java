package com.kakaoseventeen.dogwalking.chat.chatroom.service;


import com.kakaoseventeen.dogwalking.chat.domain.ChatMessage;
import com.kakaoseventeen.dogwalking.chat.domain.ChatRoom;
import com.kakaoseventeen.dogwalking.chat.domain.MessageType;
import com.kakaoseventeen.dogwalking.chat.dto.ChatListResDTO;
import com.kakaoseventeen.dogwalking.chat.repository.ChatMessageRepository;
import com.kakaoseventeen.dogwalking.chat.repository.ChatRoomRepository;
import com.kakaoseventeen.dogwalking.chat.service.ChatRoomReadService;
import com.kakaoseventeen.dogwalking.dog.domain.Dog;
import com.kakaoseventeen.dogwalking.dog.repository.DogJpaRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.notification.repository.NotificationJpaRepository;
import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import com.kakaoseventeen.dogwalking.walk.domain.WalkStatus;
import com.kakaoseventeen.dogwalking.walk.repository.WalkRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@SpringBootTest
public class ChatRoomReadServiceTest {

    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private MemberJpaRepository memberJpaRepository;
    @Autowired
    private WalkRepository walkRepository;
    @Autowired
    private DogJpaRepository dogJpaRepository;
    @Autowired
    private NotificationJpaRepository notificationJpaRepository;
    @Autowired
    private ChatRoomReadService chatRoomReadService;

    @Autowired
    private EntityManager em;


    @BeforeEach
    public void setUp(){
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

        // chatRoom
        ChatRoom chatRoom1 = ChatRoom.builder()
                .appMemberId(member2)
                .notiMemberId(member1)
                .build();
        chatRoomRepository.save(chatRoom1);

        // chatMessage
        ChatMessage chatMessage1 = ChatMessage.builder()
                .chatRoomId(chatRoom1)
                .content("테스트 컨텐츠1")
                .messageType(MessageType.CHAT)
                .senderId(member1)
                .build();
        ChatMessage chatMessage2 = ChatMessage.builder()
                .chatRoomId(chatRoom1)
                .content("테스트 컨텐츠2")
                .messageType(MessageType.CHAT)
                .senderId(member2)
                .build();
        chatMessageRepository.saveAll(List.of(chatMessage1, chatMessage2));

        // Dog
        Dog dog = Dog.builder()
                .member(member1)
                .name("강아지")
                .size("size")
                .breed("breed")
                .sex("sex")
                .build();
        dogJpaRepository.save(dog);

        // Notification
        Notification notification = Notification.builder()
                .dog(dog)
                .title("테스트 타이틀")
                .lat(24.0)
                .lng(24.1)
                .coin(BigDecimal.ONE)
                .significant("sig")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59)))
                .build();
        notificationJpaRepository.save(notification);

        // walk
        Walk walk = Walk.builder()
                .master(member1)
                .walker(member2)
                .notification(notification)
                .walkStatus(WalkStatus.READY)
                .build();
        walkRepository.save(walk);

//        em.flush();
        em.clear();
    }

    @DisplayName("채팅방 목록 조회 테스트")
    @Test
    @WithMockUser(username = "test1@naver.com", roles = "ADMIN")
    public void get_chat_list_test() {
        // given
        Long userId = 1L;

        // when
        List<ChatListResDTO> response = chatRoomReadService.getChatList();

        // then
        Assertions.assertEquals(1L, response.get(0).chatRoomId());
    }


}
