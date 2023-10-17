package com.kakaoseventeen.dogwalking.walk;

import com.kakaoseventeen.dogwalking.application.domain.Application;
import com.kakaoseventeen.dogwalking.application.repository.ApplicationRepository;
import com.kakaoseventeen.dogwalking.match.domain.Match;
import com.kakaoseventeen.dogwalking.match.repository.MatchingRepository;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.notification.repository.NotificationJpaRepository;
import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import com.kakaoseventeen.dogwalking.walk.repository.WalkRepository;
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
import java.time.LocalDateTime;
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

    @Autowired
    NotificationJpaRepository notificationJpaRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    MatchingRepository matchingRepository;

    private final long memberId = 1;

    @BeforeEach
    public void setUp() {
        Member master = Member.builder()
                .email("yardyard@likelion.org")
                .password("asd1111")
                .profileContent("반갑다리요")
                .dogBowl(50)
                .nickname("견주유저1")
                .coin(BigDecimal.valueOf(3000))
                .build();

        memberJpaRepository.saveAndFlush(master);

        Member walker = Member.builder()
                .email("yardyard2@likelion.org")
                .password("asd1112")
                .profileContent("반갑다리요2")
                .dogBowl(50)
                .nickname("알바유저1")
                .coin(BigDecimal.valueOf(4000))
                .build();

        memberJpaRepository.saveAndFlush(walker);

        Dog dog = Dog.builder()
                .breed("요크셔테리어")
                .sex("Male")
                .member(master)
                .image("https:123123.jpeg")
                .size("소형견")
                .name("복슬이")
                .build();

        dogJpaRepository.saveAndFlush(dog);

        Notification notification = Notification.builder()
                .title("우리 강아지 좀 봐주세요")
                .lat(2.123123)
                .lng(31.12312)
                .startTime(LocalDateTime.MIN)
                .endTime(LocalDateTime.MAX)
                .significant("요크 셔테리어")
                .coin(BigDecimal.valueOf(3000))
                .build();

        notificationJpaRepository.saveAndFlush(notification);

        Walk walk1 = Walk.of(master, walker, notification);
        repository.saveAndFlush(walk1);

        Application application = Application.builder()
                .appMemberId(walker)
                .aboutMe("저는 이승건입니다.")
                .build();

        applicationRepository.saveAndFlush(application);

        Match match = Match.builder()
                .applicationId(application)
                .notificationId(notification)
                .build();

        matchingRepository.saveAndFlush(match);

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
        산책 허락하기 메서드
     */
    @Test
    public void test_3() {
        // given
        Long matchingId = 1L;

        // when
        Notification notification = matchingRepository.findMatchById(matchingId).getNotificationId();

        // then
        Assertions.assertEquals(notification.getTitle(), "우리 강아지 좀 봐주세요");
    }


    /*
    산책 시작하기 메서드
    */
    @Test
    public void test_4() {
        // given
        Long matchingId = 1L;

        // when
        Walk walk = matchingRepository.findWalkFromMatchById(matchingId).get();

        // then
        Assertions.assertEquals(walk.getId(), 1L);
    }
}
