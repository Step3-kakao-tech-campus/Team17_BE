package com.kakaoseventeen.dogwalking.chat.chatroom.repository;

import com.kakaoseventeen.dogwalking.chat.domain.ChatMessage;
import com.kakaoseventeen.dogwalking.chat.domain.ChatRoom;
import com.kakaoseventeen.dogwalking.chat.domain.MessageType;
import com.kakaoseventeen.dogwalking.chat.repository.ChatMessageRepository;
import com.kakaoseventeen.dogwalking.chat.repository.ChatRoomRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

/**
 * ChatRoomRepository Data JPA 쿼리 응답 테스트
 *
 * @author 박영규
 * @version 1.0
 */
@DataJpaTest
public class ChatRoomRepositoryTest {

    @Autowired
    ChatRoomRepository chatRoomRepository;
    @Autowired
    MemberJpaRepository memberJpaRepository;


    @PersistenceContext
    private EntityManager em;
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @BeforeEach
    public void setUp(){

        Member member1 = Member.builder()
                .email("test1@naver.com")
                .password("test1")
                .nickname("test1")
                .build();
        Member member2 = Member.builder()
                .email("test2@naver.com")
                .password("test2")
                .nickname("test2")
                .build();
        memberJpaRepository.saveAll(List.of(member1, member2));

        ChatRoom chatRoom1 = ChatRoom.builder()
                .notiMemberId(member1)
                .appMemberId(member2)
                .build();

        ChatRoom chatRoom2 = ChatRoom.builder()
                .notiMemberId(member2)
                .appMemberId(member1)
                .build();
        chatRoomRepository.saveAll(List.of(chatRoom1, chatRoom2));

        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoomId(chatRoom1)
                .content("채팅 테스트1")
                .senderId(member1)
                .messageType(MessageType.CHAT)
                .build();
        chatMessageRepository.saveAndFlush(chatMessage);

        em.flush();
        em.clear();

    }

    @DisplayName("채팅 방 목록 조회 쿼리 테스트")
    @Test
    public void findChatRoomsByAppMemberIdOrNotiMemberId_test() {
        // given
        Member member = Member.builder()
                .id(1L)
                .email("test1@naver.com")
                .password("test1")
                .nickname("test1")
                .build();
        Long index =1L;


        // when
        List<ChatRoom> chatRooms = chatRoomRepository.findChatRoomsByAppMemberIdOrNotiMemberId(member, member);

        // then
        Assertions.assertEquals(2, chatRooms.size());

        System.out.println("index 0 : " + chatRooms.get(0).getChatRoomId());
        System.out.println("index 1 : " + chatRooms.get(1).getChatRoomId());

        Assertions.assertEquals(index, chatRooms.get(0).getChatRoomId());
        Assertions.assertEquals(index+1, chatRooms.get(1).getChatRoomId());

        Assertions.assertEquals("test1@naver.com", chatRooms.get(0).getNotiMemberId().getEmail());
        Assertions.assertEquals("test2@naver.com", chatRooms.get(0).getAppMemberId().getEmail());


    }

    @DisplayName("채팅방 목록 조회 쿼리 테스트2")
    @Test
    public void findTop1ByChatRoomIdOrderByChatMessageIdDesc() {
        // given
        ChatRoom chatRoom = chatRoomRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("채팅방이 존재하지 않음."));
        String expectedString = "채팅 테스트1";
        System.out.println("채팅방 이름: " + chatRoom.getChatRoomId());

        // when
        ChatMessage chatMessage = chatMessageRepository.findTop1ByChatRoomIdOrderByChatMessageIdDesc(chatRoom)
                .orElseThrow(() -> new RuntimeException("채팅 메세지가 존재하지 않음."));

        // then
        Assertions.assertEquals(expectedString,chatMessage.getContent());
    }
}
