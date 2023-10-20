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
                .nickname("닉네임")
                .email("mkwak1125@gmail.com")
                .password("kwak!6038")
                .dogBowl(55)
                .profileImage("1번 이미지")
                .coin(BigDecimal.valueOf(3000))
                .build();

        Member member2 = Member.builder()
                .id(2L)
                .nickname("닉네임")
                .email("asfd@gmail.com")
                .password("kwak!6038")
                .dogBowl(55)
                .coin(BigDecimal.valueOf(3000))
                .build();

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
                .member(member1)
                .build();

        Dog dog3 = Dog.builder()
                .id(3L)
                .breed("시바견")
                .name("강아지이름3")
                .sex("암컷")
                .size("소형견")
                .member(member2)
                .build();

        Dog dog4 = Dog.builder()
                .id(4L)
                .breed("푸들")
                .name("강아지이름4")
                .sex("수컷")
                .size("대형견")
                .member(member1)
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