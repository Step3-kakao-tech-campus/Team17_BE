package com.kakaoseventeen.dogwalking.walk;

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

@DataJpaTest
public class WalkRespTest {

    @Autowired
    WalkRepository repository;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Autowired
    DogJpaRepository dogJpaRepository;

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

        Walk walk1 = Walk.of(dog, member);
        walk1.updateStatus(WalkStatus.END);
        repository.saveAndFlush(walk1);

        Walk walk2 = Walk.of(dog, member);
        walk2.updateStatus(WalkStatus.END);
        repository.saveAndFlush(walk2);

        Walk walk3 = Walk.of(dog, member);
        repository.saveAndFlush(walk3);
    }

    /*
    UserId로 Walk를 조회하는 쿼리 테스트
     */
    @Test
    public void test_1() {
    // given
    Member member = memberJpaRepository.findById(1).get();

    // when
    List<Walk> walks = repository.findByWalkStatus(member.getId());

    // then
    Assertions.assertEquals(walks.size(), 1);
    Assertions.assertEquals(walks.get(0).getDog().getName(), "복슬이");
    }


    /*
    UserId와 산책의 상태가 END인 Walk 엔티티를 가져오는 쿼리
     */
    @Test
    public void test_2() {
        // given
        Member member = memberJpaRepository.findById(1).get();

        // when
        List<Walk> walks = repository.findByWalkWithUserIdAndEndStatus(member.getId());

        // then
        Assertions.assertEquals(walks.size(), 2);
        Assertions.assertEquals(walks.get(0).getWalkStatus().toString(), "END");
    }
}
