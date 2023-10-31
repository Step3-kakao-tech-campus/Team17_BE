package com.kakaoseventeen.dogwalking.notification.controller;

import com.kakaoseventeen.dogwalking.application.domain.Application;
import com.kakaoseventeen.dogwalking.application.repository.ApplicationRepository;
import com.kakaoseventeen.dogwalking.dog.domain.Dog;
import com.kakaoseventeen.dogwalking.dog.repository.DogJpaRepository;
import com.kakaoseventeen.dogwalking.match.domain.Match;
import com.kakaoseventeen.dogwalking.match.repository.MatchingRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.notification.repository.NotificationJpaRepository;
import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import com.kakaoseventeen.dogwalking.walk.repository.WalkRepository;
import com.kakaoseventeen.dogwalking.walkRoad.domain.WalkRoad;
import com.kakaoseventeen.dogwalking.walkRoad.repository.WalkRoadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

@RestController
@RequiredArgsConstructor
public class InitController {

    private final DogJpaRepository dogJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final NotificationJpaRepository notificationJpaRepository;
    private final ApplicationRepository applicationRepository;
    private final WalkRepository walkRepository;
    private final MatchingRepository matchingRepository;
    private final WalkRoadRepository walkRoadRepository;

    @GetMapping("/init")
    public ResponseEntity<?> init(){


        Member member1 = memberJpaRepository.save(Member.builder()
                .id(1L)
                .nickname("닉네임1")
                .email("mkwak1125@gmail.com")
                .password("kwak!6038")
                .dogBowl(55)
                .profileImage("1번 이미지")
                .coin(BigDecimal.valueOf(100000))
                .build());


        Member member2 = memberJpaRepository.save(Member.builder()
                .id(2L)
                .nickname("닉네임2")
                .email("asfd@gmail.com")
                .password("kwak!6038")
                .dogBowl(55)
                .coin(BigDecimal.valueOf(100000))
                .build());

        Member member3 = memberJpaRepository.save(Member.builder()
                .id(3L)
                .nickname("닉네임3")
                .email("yardyard@gmail.com")
                .password("12341234!")
                .dogBowl(55)
                .coin(BigDecimal.valueOf(100000))
                .build());

        Member member4 = memberJpaRepository.save(Member.builder()
                .id(4L)
                .nickname("닉네임4")
                .email("yardyard@naver.com")
                .password("12341234!")
                .dogBowl(55)
                .coin(BigDecimal.valueOf(100000))
                .build());


        Dog dog1 = Dog.builder()
                .id(1L)
                .breed("푸들")
                .name("강아지이름1")
                .sex("수컷")
                .size("대형견")
                .member(member1)
                .build();

        Dog dog2 = Dog.builder()
                .id(2L)
                .breed("말티즈")
                .name("강아지이름2")
                .sex("암컷")
                .size("소형견")
                .member(member2)
                .build();

        Dog dog3 = Dog.builder()
                .id(3L)
                .breed("시바견")
                .name("강아지이름3")
                .sex("암컷")
                .size("소형견")
                .member(member3)
                .build();

        Dog dog4 = Dog.builder()
                .id(4L)
                .breed("푸들")
                .name("강아지이름4")
                .sex("수컷")
                .size("대형견")
                .member(member4)
                .build();

        Dog dog5 = Dog.builder()
                .id(5L)
                .breed("푸들")
                .name("강아지이름5")
                .sex("수컷")
                .size("대형견")
                .member(member1)
                .build();

        Dog dog6 = Dog.builder()
                .id(6L)
                .breed("비글")
                .name("강아지이름6")
                .sex("수컷")
                .size("중형견")
                .member(member1)
                .build();

        dogJpaRepository.save(dog1);
        dogJpaRepository.save(dog2);
        dogJpaRepository.save(dog3);
        dogJpaRepository.save(dog4);
        dogJpaRepository.save(dog5);
        dogJpaRepository.save(dog6);

        Notification notification1 = Notification.builder()
                .dog(dog1)
                .title("제목1 공통")
                .lat(34.25)
                .lng(40.1)
                .coin(BigDecimal.valueOf(40000))
                .startTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 22, 36))
                .endTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 23, 36))
                .significant("우리 아이는 검색1 착해용 ")
                .build();

        Notification notification2 = Notification.builder()
                .dog(dog2)
                .title("제목2 공통")
                .lat(34.25)
                .lng(50.1)
                .coin(BigDecimal.valueOf(3500))
                .startTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 22, 36))
                .endTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 23, 36))
                .significant("우리 아이는 검색2 착해용ㅋㅋㅋ ")
                .build();

        Notification notification3 = Notification.builder()
                .dog(dog3)
                .title("제목3 공통")
                .lat(34.25)
                .lng(60.1)
                .coin(BigDecimal.valueOf(3000))
                .startTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 22, 36))
                .endTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 23, 36))
                .significant("우리 아이는 착해용ㅋㅋㅋㅋㅋㅋㅋ")
                .build();

        Notification notification4 = Notification.builder()
                .dog(dog3)
                .title("제목4")
                .lat(34.25)
                .lng(70.1)
                .coin(BigDecimal.valueOf(500))
                .startTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 22, 36))
                .endTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 23, 36))
                .significant("우리 아이는 착해용ㅋㅋㅋㅋㅋㅋㅋ")
                .build();


        Notification notification5 = Notification.builder()
                .dog(dog5)
                .title("제목5 호호")
                .lat(34.25)
                .lng(80.1)
                .coin(BigDecimal.valueOf(40000))
                .startTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 22, 36))
                .endTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 23, 36))
                .significant("우리 아이는 착해용")
                .build();

        Notification notification6 = Notification.builder()
                .dog(dog6)
                .title("제목6 호호")
                .lat(34.25)
                .lng(90.1)
                .coin(BigDecimal.valueOf(40000))
                .startTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 22, 36))
                .endTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 23, 36))
                .significant("우리 아이는 착해용 공통")
                .build();

        Notification notification7 = Notification.builder()
                .dog(dog4)
                .title("제목7")
                .lat(34.25)
                .lng(100.1)
                .coin(BigDecimal.valueOf(40000))
                .startTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 22, 36))
                .endTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 23, 36))
                .significant("우리 아이는 착해용 공통")
                .build();

        notificationJpaRepository.save(notification1);
        notificationJpaRepository.save(notification2);
        notificationJpaRepository.save(notification3);
        notificationJpaRepository.save(notification4);
        notificationJpaRepository.save(notification5);
        notificationJpaRepository.save(notification6);
        notificationJpaRepository.save(notification7);

        Walk walk1 = walkRepository.saveAndFlush(Walk.of(member1, member2, notification1));

        Walk walk2 = walkRepository.saveAndFlush(Walk.of(member3, member4, notification2));

        walkRoadRepository.saveAndFlush(WalkRoad.of(37.402056,127.108212, walk1));

        Application application1 = applicationRepository.saveAndFlush(Application.builder()
                .aboutMe("저에 관해서 소개를 하겠습니다.")
                .appMemberId(member2)
                .certification("애견 보호사 2급")
                .experience("강아지 유치원 2년 근무")
                .build());

        Application application2 = applicationRepository.saveAndFlush(Application.builder()
                .aboutMe("저에 관해서 소개를 하겠습니다.")
                .appMemberId(member3)
                .certification("애견 보호사 2급")
                .experience("강아지 유치원 2년 근무")
                .build());

        matchingRepository.saveAndFlush(Match.builder()
                .notificationId(notification1)
                .applicationId(application1)
                .build());

        matchingRepository.saveAndFlush(Match.builder()
                .notificationId(notification2)
                .applicationId(application2)
                .build());

        return ResponseEntity.ok("init");
    }
}