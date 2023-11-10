package com.kakaoseventeen.dogwalking.review.service;

import com.kakaoseventeen.dogwalking.dog.domain.Dog;
import com.kakaoseventeen.dogwalking.dog.repository.DogJpaRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.notification.repository.NotificationJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 리뷰 작성하기 단위 테스트
 *
 * @author 박영규
 * @version 1.0
 */
@DataJpaTest
public class ReviewWriteServiceTest {

    @PersistenceContext
    private EntityManager em;


    @Autowired
    NotificationJpaRepository notificationJpaRepository;
    @Autowired
    MemberJpaRepository memberJpaRepository;
    @Autowired
    DogJpaRepository dogJpaRepository;

    @BeforeEach
    public void setUp(){
        Member member = Member.builder()
                .nickname("테스트닉네임")
                .email("test@naver.com")
                .password("testPassword")
                .build();
        memberJpaRepository.save(member);

        Dog dog = Dog.builder()
                .name("테스트 개이름")
                .sex("수컷")
                .breed("testBreed")
                .size("testSize")
                .member(member)
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
        em.flush();
        em.clear();
    }

    @DisplayName("리뷰 작성 쿼리 테스트")
    @Test
    public void create_review_test() {
        // given
        Long notiId = 1L;

        // when
        Member member = notificationJpaRepository.mfindMember(notiId).getDog().getMember();

        // then
        Assertions.assertEquals("테스트닉네임",member.getNickname());

    }
}
