package com.kakaoseventeen.dogwalking.walk;

import com.kakaoseventeen.dogwalking.chat.domain.ChatRoom;
import com.kakaoseventeen.dogwalking.chat.repository.ChatRoomRepository;
import com.kakaoseventeen.dogwalking.dog.Dog;
import com.kakaoseventeen.dogwalking.dog.DogJpaRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;
import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import com.kakaoseventeen.dogwalking.walk.domain.WalkStatus;
import com.kakaoseventeen.dogwalking.walk.repository.WalkRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class WalkRespTest {

    @Autowired
    WalkRepository repository;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Autowired
    DogJpaRepository dogJpaRepository;

    @Autowired
    ChatRoomRepository chatRoomRepository;

    private final long memberId = 1;

    @BeforeEach
    public void setUp() {
        Member member1 = Member.builder()
                .email("yardyard@likelion.org")
                .password("asd1111")
                .profileContent("반갑다리요")
                .dogBowl(50)
                .nickname("견주유저1")
                .coin(BigDecimal.valueOf(3000))
                .build();

        memberJpaRepository.saveAndFlush(member1);

        Member member2 = Member.builder()
                .email("yardyard2@likelion.org")
                .password("asd1112")
                .profileContent("반갑다리요2")
                .dogBowl(50)
                .nickname("알바유저1")
                .coin(BigDecimal.valueOf(4000))
                .build();

        memberJpaRepository.saveAndFlush(member2);

        Dog dog = Dog.builder()
                .breed("요크셔테리어")
                .sex("Male")
                .member(member1)
                .image("https:123123.jpeg")
                .size("소형견")
                .name("복슬이")
                .build();

        dogJpaRepository.saveAndFlush(dog);

        ChatRoom chatRoom1 = chatRoomRepository.saveAndFlush(new ChatRoom());

        Walk walk1 = Walk.of(member1, member2, chatRoom1);
        repository.saveAndFlush(walk1);

        ChatRoom chatRoom2 = chatRoomRepository.saveAndFlush(new ChatRoom());

        Walk walk2 = Walk.of(member1, member2, chatRoom2);
        repository.saveAndFlush(walk2);
//
//        Walk walk3 = Walk.of(dog, member1, member2, chatRoom);
//        repository.saveAndFlush(walk3);
    }

    /*
        UserId로 해당 User의 모든 Walk를 조회하는 쿼리 테스트
     */
    @Test
    public void test_1() {
    // given
    Member member = memberJpaRepository.findById(memberId).get();

    // when
    List<Walk> walks = repository.findByWalkStatus(member.getId());

    // then
    Assertions.assertEquals(walks.size(), 2);
    }


    /*
    UserId와 산책의 상태가 END인 Walk 엔티티를 가져오는 쿼리
     */
    @Test
    public void test_2() {
        // given
        Member member = memberJpaRepository.findById(memberId).get();
        List<Walk> walks = repository.findByWalkWithUserIdAndEndStatus(member.getId());
        Walk walk1 = walks.get(0);

        // when
        walk1.endWalk();

        // then
        Assertions.assertEquals(walks.size(), 2);
        Assertions.assertEquals(walks.get(0).getWalkStatus().toString(), "END");
    }

    /*
        ChannelId의 외래키를 지닌 Walk 엔티티를 가져오는 쿼리
     */
    @Test
    public void test_3() {
        // given
        Long chatRoomId = 1L;

        // when
        Optional<Walk> walk = repository.findWalkByChatRoomId(chatRoomId);

        // then
        Assertions.assertEquals(walk.get().getChatRoom().getId(), 1L);
    }
}
