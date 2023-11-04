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
import com.kakaoseventeen.dogwalking.review.domain.Review;
import com.kakaoseventeen.dogwalking.review.repository.ReviewRepository;
import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import com.kakaoseventeen.dogwalking.walk.repository.WalkRepository;
import com.kakaoseventeen.dogwalking.walkRoad.domain.WalkRoad;
import com.kakaoseventeen.dogwalking.walkRoad.repository.WalkRoadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final ReviewRepository reviewRepository;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/init")
    public ResponseEntity<?> init(){


        Member member1 = memberJpaRepository.save(Member.builder()
                .id(1L)
                .nickname("닉네임1")
                .email("mkwak1125@gmail.com")
                .password(passwordEncoder.encode("kwak!6038"))
                .profileImage("1번 이미지")
                .profileContent("나는 1번 멤버")
                .coin(BigDecimal.valueOf(100000))
                .build());



        Member member2 = memberJpaRepository.save(Member.builder()
                .id(2L)
                .nickname("닉네임2")
                .email("asfd@gmail.com")
                .password(passwordEncoder.encode("kwak!6038"))
                .profileContent("나는 2번 멤버")
                .profileImage("2번 이미지")
                .dogBowl(80)
                .coin(BigDecimal.valueOf(500000))
                .dogBowl(55)
                .build());

        Member member3 = memberJpaRepository.save(Member.builder()
                .id(3L)
                .nickname("닉네임3")
                .email("yardyard@gmail.com")
                .password(passwordEncoder.encode("yard!1234"))
                .dogBowl(55)
                .coin(BigDecimal.valueOf(100000))
                .build());

        Member member4 = memberJpaRepository.save(Member.builder()
                .id(4L)
                .nickname("닉네임4")
                .email("yardyard@naver.com")
                .password(passwordEncoder.encode("yard!1234"))
                .dogBowl(55)
                .coin(BigDecimal.valueOf(100000))
                .build());


        Dog dog1 = Dog.builder()
                .id(1L)
                .breed("푸들")
                .name("강아지이름1")
                .sex("수컷")
                .size("대형견")
                .specificity("알러지있음")
                .image("1번 강아지 이미지")
                .age(3)
                .member(member1)
                .build();

        Dog dog2 = Dog.builder()
                .id(2L)
                .breed("말티즈")
                .name("강아지이름2")
                .sex("암컷")
                .size("소형견")
                .specificity("알러지있음")
                .image("2번 강아지 이미지")
                .age(6)
                .member(member2)
                .build();

        Dog dog3 = Dog.builder()
                .id(3L)
                .breed("시바견")
                .name("강아지이름3")
                .sex("암컷")
                .size("중형견")
                .specificity("알러지있음")
                .image("3번 강아지 이미지")
                .age(10)
                .size("소형견")
                .member(member3)
                .build();

        Dog dog4 = Dog.builder()
                .id(4L)
                .breed("푸들")
                .name("강아지이름4")
                .sex("수컷")
                .size("대형견")
                .specificity("알러지있음")
                .image("4번 강아지 이미지")
                .age(1)
                .member(member4)
                .build();

        Dog dog5 = Dog.builder()
                .id(5L)
                .breed("푸들")
                .name("강아지이름5")
                .sex("수컷")
                .size("대형견")
                .specificity("알러지있음")
                .image("5번 강아지 이미지")
                .age(15)
                .member(member2)
                .build();

        Dog dog6 = Dog.builder()
                .id(6L)
                .breed("비글")
                .name("강아지이름6")
                .sex("수컷")
                .size("중형견")
                .specificity("알러지있음")
                .image("6번 강아지 이미지")
                .age(12)
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
                .lat(35.17)
                .lng(126.90)
                .coin(BigDecimal.valueOf(400))
                .startTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 20, 36))
                .endTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 22, 36))
                .significant("우리 아이는 착해용")
                .build();

        Notification notification2 = Notification.builder()
                .dog(dog2)
                .title("제목2 공통")
                .lat(35.18)
                .lng(126.91)
                .coin(BigDecimal.valueOf(350))
                .startTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 22, 36))
                .endTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 23, 36))
                .significant("우리 아이는 착해용")
                .build();

        Notification notification3 = Notification.builder()
                .dog(dog3)
                .title("제목3 공통")
                .lat(35.19)
                .lng(126.92)
                .coin(BigDecimal.valueOf(300))
                .startTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 22, 36))
                .endTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 23, 36))
                .significant("우리 아이는 착해용")
                .build();

        Notification notification4 = Notification.builder()
                .dog(dog3)
                .title("제목4 호호")
                .lat(35.20)
                .lng(126.93)
                .coin(BigDecimal.valueOf(500))
                .startTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 22, 36))
                .endTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 23, 36))
                .significant("우리 아이는 착해용")
                .build();


        Notification notification5 = Notification.builder()
                .dog(dog5)
                .title("제목5 호호")
                .lat(35.21)
                .lng(126.94)
                .coin(BigDecimal.valueOf(400))
                .startTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 22, 36))
                .endTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 23, 36))
                .significant("우리 아이는 착해용")
                .build();

        Notification notification6 = Notification.builder()
                .dog(dog6)
                .title("제목6 검색 테스트")
                .lat(35.22)
                .lng(126.95)
                .coin(BigDecimal.valueOf(400))
                .startTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 22, 36))
                .endTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 23, 36))
                .significant("우리 아이는 착해용")
                .build();

        Notification notification7 = Notification.builder()
                .dog(dog4)
                .title("제목7 검색 테스트")
                .lat(35.23)
                .lng(126.96)
                .coin(BigDecimal.valueOf(400))
                .startTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 22, 36))
                .endTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 23, 36))
                .significant("우리 아이는 착해용")
                .build();

        notificationJpaRepository.save(notification1);
        notificationJpaRepository.save(notification2);
        notificationJpaRepository.save(notification3);
        notificationJpaRepository.save(notification4);
        notificationJpaRepository.save(notification5);
        notificationJpaRepository.save(notification6);
        notificationJpaRepository.save(notification7);

        Walk walk1 = walkRepository.saveAndFlush(Walk.of(member1, member2, notification1));


        walkRoadRepository.saveAndFlush(WalkRoad.of(37.402056,127.108212, walk1));

        Application application1 = applicationRepository.saveAndFlush(Application.builder()
                .aboutMe("저에 관해서 소개를 하겠습니다. 1번 지원자")
                .title("지원서 제목1")
                .appMemberId(member1)
                .certification("애견 보호사 2급")
                .experience("강아지 유치원 2년 근무")
                .build());

        Application application2 = applicationRepository.saveAndFlush(Application.builder()
                .aboutMe("저에 관해서 소개를 하겠습니다. 2번 지원자")
                .title("지원서 제목2")
                .appMemberId(member2)
                .certification("애견 보호사 2급")
                .experience("강아지 유치원 2년 근무")
                .build());

        Application application3 = applicationRepository.saveAndFlush(Application.builder()
                .aboutMe("저에 관해서 소개를 하겠습니다. 3번 지원자")
                .title("지원서 제목3")
                .appMemberId(member1)
                .certification("애견 보호사 2급")
                .experience("강아지 유치원 2년 근무")
                .build());

        Application application4 = applicationRepository.saveAndFlush(Application.builder()
                .aboutMe("저에 관해서 소개를 하겠습니다. 4번 지원자")
                .title("지원서 제목4")
                .appMemberId(member2)
                .certification("애견 보호사 2급")
                .experience("강아지 유치원 2년 근무")
                .build());

        matchingRepository.saveAndFlush(Match.builder()
                .notificationId(notification1)
                .applicationId(application1)
                .build());

        matchingRepository.saveAndFlush(Match.builder()
                .notificationId(notification1)
                .applicationId(application3)
                .build());

        matchingRepository.saveAndFlush(Match.builder()
                .notificationId(notification1)
                .applicationId(application4)
                .build());

        matchingRepository.saveAndFlush(Match.builder()
                .notificationId(notification2)
                .applicationId(application2)
                .build());

        reviewRepository.saveAndFlush(Review.builder()
                .reviewContent("이 집 맛집이네용1~")
                .receiverId(member2)
                .reviewerId(member1)
                .reviewEval1(true)
                .reviewEval2(true)
                .reviewEval3(true)
                .reviewEval4(false)
                .dogBowl(40)
                .isReceiverDogOwner(false)
                .build());

        reviewRepository.saveAndFlush(Review.builder()
                .reviewContent("이 집 맛집이네용2~")
                .receiverId(member3)
                .reviewerId(member1)
                .reviewEval1(true)
                .reviewEval2(true)
                .reviewEval3(true)
                .reviewEval4(false)
                .dogBowl(40)
                .isReceiverDogOwner(false)
                .build());

        reviewRepository.saveAndFlush(Review.builder()
                .reviewContent("이 집 맛집이네용3~")
                .receiverId(member1)
                .reviewerId(member4)
                .reviewEval1(true)
                .reviewEval2(true)
                .reviewEval3(true)
                .reviewEval4(false)
                .dogBowl(40)
                .isReceiverDogOwner(true)
                .build());

        return ResponseEntity.ok("init");
    }
}