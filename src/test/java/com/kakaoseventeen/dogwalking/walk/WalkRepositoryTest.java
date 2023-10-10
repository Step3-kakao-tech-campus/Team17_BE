package com.kakaoseventeen.dogwalking.walk;

import com.kakaoseventeen.dogwalking.dog.Dog;
import com.kakaoseventeen.dogwalking.dog.DogJpaRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;
import com.kakaoseventeen.dogwalking.walk.domain.Walk;
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
public class WalkRepositoryTest {

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


        Walk walk = Walk.of(dog, member);
        repository.saveAndFlush(walk);
    }

    @Test
    public void findAllMentiByDefaultSorting_test() {
    // given
    Member member = memberJpaRepository.findById(1).get();

    // when
    List<Walk> walks = repository.findByWalkStatus(member.getId());

    // then
    Assertions.assertEquals(walks.size(), 1);
    Assertions.assertEquals(walks.get(0).getDog().getName(), "복슬이");
    }
}
