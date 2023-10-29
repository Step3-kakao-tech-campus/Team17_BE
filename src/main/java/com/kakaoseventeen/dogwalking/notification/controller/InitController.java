package com.kakaoseventeen.dogwalking.notification.controller;

import com.kakaoseventeen.dogwalking.dog.domain.Dog;
import com.kakaoseventeen.dogwalking.dog.repository.DogJpaRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.notification.repository.NotificationJpaRepository;
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

    @GetMapping("/init")
    public ResponseEntity<?> init(){

        Member member1 = Member.builder()
                .id(1L)
                .nickname("민주")
                .email("mkwak1125@gmail.com")
                .password("kwak!6038")
                .profileImage("1번 이미지")
                .profileContent("나는 1번 멤버")
                .build();

        Member member2 = Member.builder()
                .id(2L)
                .nickname("승건")
                .email("asfd@gmail.com")
                .password("kwak!6038")
                .profileContent("나는 2번 멤버")
                .dogBowl(80)
                .coin(BigDecimal.valueOf(500000))
                .build();

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
                .member(member1)
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
                .member(member2)
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
                .member(member1)
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

        Notification notification1 = Notification.builder()
                .dog(dog1)
                .title("제목1 공통")
                .lat(34.25)
                .lng(40.1)
                .coin(BigDecimal.valueOf(400))
                .startTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 20, 36))
                .endTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 22, 36))
                .significant("우리 아이는 착해용")
                .build();

        Notification notification2 = Notification.builder()
                .dog(dog2)
                .title("제목2 공통")
                .lat(34.25)
                .lng(50.1)
                .coin(BigDecimal.valueOf(350))
                .startTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 22, 36))
                .endTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 23, 36))
                .significant("우리 아이는 착해용")
                .build();

        Notification notification3 = Notification.builder()
                .dog(dog3)
                .title("제목3 공통")
                .lat(34.25)
                .lng(60.1)
                .coin(BigDecimal.valueOf(300))
                .startTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 22, 36))
                .endTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 23, 36))
                .significant("우리 아이는 착해용")
                .build();

        Notification notification4 = Notification.builder()
                .dog(dog3)
                .title("제목4 호호")
                .lat(34.25)
                .lng(70.1)
                .coin(BigDecimal.valueOf(500))
                .startTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 22, 36))
                .endTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 23, 36))
                .significant("우리 아이는 착해용")
                .build();


        Notification notification5 = Notification.builder()
                .dog(dog5)
                .title("제목5 호호")
                .lat(34.25)
                .lng(80.1)
                .coin(BigDecimal.valueOf(400))
                .startTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 22, 36))
                .endTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 23, 36))
                .significant("우리 아이는 착해용")
                .build();

        Notification notification6 = Notification.builder()
                .dog(dog6)
                .title("제목6 검색 테스트")
                .lat(34.25)
                .lng(90.1)
                .coin(BigDecimal.valueOf(400))
                .startTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 22, 36))
                .endTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 23, 36))
                .significant("우리 아이는 착해용")
                .build();

        Notification notification7 = Notification.builder()
                .dog(dog4)
                .title("제목7 검색 테스트")
                .lat(34.25)
                .lng(100.1)
                .coin(BigDecimal.valueOf(400))
                .startTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 22, 36))
                .endTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 23, 36))
                .significant("우리 아이는 착해용")
                .build();




        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);
        dogJpaRepository.save(dog1);
        dogJpaRepository.save(dog2);
        dogJpaRepository.save(dog3);
        dogJpaRepository.save(dog4);
        dogJpaRepository.save(dog5);
        dogJpaRepository.save(dog6);
        notificationJpaRepository.save(notification1);
        notificationJpaRepository.save(notification2);
        notificationJpaRepository.save(notification3);
        notificationJpaRepository.save(notification4);
        notificationJpaRepository.save(notification5);
        notificationJpaRepository.save(notification6);
        notificationJpaRepository.save(notification7);



        return ResponseEntity.ok("init");
    }
}