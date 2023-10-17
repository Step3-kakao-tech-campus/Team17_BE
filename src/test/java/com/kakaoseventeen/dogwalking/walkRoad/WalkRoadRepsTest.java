package com.kakaoseventeen.dogwalking.walkRoad;

import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import com.kakaoseventeen.dogwalking.walk.repository.WalkRepository;
import com.kakaoseventeen.dogwalking.walkRoad.domain.WalkRoad;
import com.kakaoseventeen.dogwalking.walkRoad.repository.WalkRoadRepository;
import com.kakaoseventeen.dogwalking.chat.model.ChatRoom;
import com.kakaoseventeen.dogwalking.chat.repository.ChatRoomRepository;
import com.kakaoseventeen.dogwalking.dog.domain.Dog;
import com.kakaoseventeen.dogwalking.dog.repository.DogJpaRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

@DataJpaTest
public class WalkRoadRepsTest {

    @Autowired
    WalkRoadRepository repository;

    @Autowired
    WalkRepository walkRepository;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Autowired
    DogJpaRepository dogJpaRepository;

    @Autowired
    ChatRoomRepository chatRoomRepository;

    @BeforeEach
    public void setUp() {
        Member member = Member.builder()
                .email("yardyard@likelion.org")
                .password("asd1111")
                .profileContent("반갑다리요")
                .dogBowl(50)
                .nickname("테스트유저1")
                .coin(BigDecimal.valueOf(3000))
                .build();

        memberJpaRepository.saveAndFlush(member);

        Dog dog = Dog.builder()
                .breed("요크셔테리어")
                .sex("Male")
                .member(member)
                .image("https:123123.jpeg")
                .size("소형견")
                .name("복슬이")
                .build();

        dogJpaRepository.saveAndFlush(dog);

        ChatRoom chatRoom = chatRoomRepository.saveAndFlush(new ChatRoom(member, member));

//        Walk walk = Walk.of(member, member, chatRoom);
//        walkRepository.saveAndFlush(walk);
//
//        for (int i = 0; i < 8; i++){
//            repository.saveAndFlush(WalkRoad.of(i, 123.12312312, walk));
//        }
    }

    /*
    WalkId로 WalkRoad를 조회하는 쿼리 테스트
     */
    @Test
    void test_1(){
        // given

        // when
        List<WalkRoad> walkRoads = repository.findWalkRoadsByWalk(1L);

        // then
        Assertions.assertEquals(walkRoads.size(), 8);
        Assertions.assertEquals(walkRoads.get(0).getLat(), 0.0);
    }
}
