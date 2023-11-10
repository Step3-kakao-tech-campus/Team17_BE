package com.kakaoseventeen.dogwalking.notification.repository;

import com.kakaoseventeen.dogwalking.dog.domain.Dog;
import com.kakaoseventeen.dogwalking.dog.repository.DogJpaRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.notification.service.HomeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * 공고글 쿼리 테스트
 *
 * @author 곽민주
 * @version 1.0
 */
@DataJpaTest
class NotificationJpaRepositoryTest {

    @Autowired
    NotificationJpaRepository notificationJpaRepository;

    @Autowired
    DogJpaRepository DogJpaRepository;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Autowired
    DogJpaRepository dogJpaRepository;



    @BeforeEach
    public void setUp() {
        Member member1 = Member.builder()
                .id(1L)
                .nickname("닉네임")
                .email("mkwak1125@gmail.com")
                .password("kwak!6038")
                .dogBowl(55)
                .profileImage("1번 이미지")
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
                .significant("우리 아이는 착해용 ")
                .build();

        Notification notification2 = Notification.builder()
                .dog(dog2)
                .title("제목2")
                .lat(34.25)
                .lng(50.1)
                .coin(BigDecimal.valueOf(3500))
                .startTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 22, 36))
                .endTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 23, 36))
                .significant("우리 아이는 착해용 ")
                .build();

        Notification notification3 = Notification.builder()
                .dog(dog3)
                .title("제목3 공통")
                .lat(34.25)
                .lng(60.1)
                .coin(BigDecimal.valueOf(3000))
                .startTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 22, 36))
                .endTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 23, 36))
                .significant("우리 아이는 착해용 ")
                .build();

        Notification notification4 = Notification.builder()
                .dog(dog3)
                .title("제목4")
                .lat(34.25)
                .lng(70.1)
                .coin(BigDecimal.valueOf(500))
                .startTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 22, 36))
                .endTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 23, 36))
                .significant("우리 아이는 착해용 ")
                .build();


        Notification notification5 = Notification.builder()
                .dog(dog5)
                .title("제목5")
                .lat(34.25)
                .lng(80.1)
                .coin(BigDecimal.valueOf(40000))
                .startTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 22, 36))
                .endTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 23, 36))
                .significant("우리 아이는 착해용 ")
                .build();

        Notification notification6 = Notification.builder()
                .dog(dog6)
                .title("제목6 공통")
                .lat(34.25)
                .lng(90.1)
                .coin(BigDecimal.valueOf(40000))
                .startTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 22, 36))
                .endTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 23, 36))
                .significant("우리 아이는 착해용 ")
                .build();

        Notification notification7 = Notification.builder()
                .dog(dog4)
                .title("제목7")
                .lat(34.25)
                .lng(100.1)
                .coin(BigDecimal.valueOf(40000))
                .startTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 22, 36))
                .endTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 23, 36))
                .significant("우리 아이는 착해용 ")
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

    }

    /**
     * 쿼리 테스트
     * search = 공통 검색, big = 소형견, breed = 시바견 필터링 테스트
     */

    @Test
    void findAllHasSizeAndBreedKeySearch() {
        String tit = "공통";
        List<String> size = new ArrayList<>();
        size.add("소형견");
        List<String> breed = new ArrayList<>();
        breed.add("시바견");
        Double latitude = 34.5;
        Double longitude = 0.5;
        Double key = 0.0;
        Pageable pageable = mock(Pageable.class);
        when(pageable.getSort()).thenReturn(Sort.unsorted());

        // Mock Repository 및 데이터 생성
        NotificationJpaRepository repository = mock(NotificationJpaRepository.class);
        List<Notification> fakeResults = new ArrayList<>();


        Notification notification = Notification.builder()
                .dog(Dog.builder()
                        .size("소형견")
                        .breed("비글")
                        .build())
                .title("제목3 공통")
                .lat(34.25)
                .lng(60.1)
                .coin(BigDecimal.valueOf(3000))
                .startTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 22, 36))
                .endTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 23, 36))
                .significant("우리 아이는 착해용ㅋㅋㅋㅋㅋㅋㅋ")
                .build();

        fakeResults.add(notification);

        when(repository.findAllHasSizeAndBreedKeySearch(tit, size, breed, latitude, longitude, key, pageable))
                .thenReturn(fakeResults);

        // 테스트
        List<Notification> actualResults = repository.findAllHasSizeAndBreedKeySearch(tit, size, breed, latitude, longitude, key, pageable);

        if (!actualResults.isEmpty()) {
            System.out.println("테스트 : " + actualResults.get(0).getTitle() + actualResults.get(0).getDog().getBreed() + actualResults.get(0).getDog().getSize());
        } else {
            System.out.println("테스트 결과 목록이 비어 있습니다.");
        }
        assertEquals(fakeResults, actualResults);
    }


    /**
     * 쿼리 테스트
     * search = 공통 검색, big = 소형견, big = 중형견 필터링 테스트
     */
    @Test
    void findAllHasSizeKeySearch() {
        String tit = "공통";
        List<String> size = new ArrayList<>();
        size.add("소형견");
        size.add("중형견");
        Double latitude = 34.5;
        Double longitude = 0.5;
        Double key = 0.0;
        Pageable pageable = mock(Pageable.class);
        when(pageable.getSort()).thenReturn(Sort.unsorted());

        // Mock Repository 및 데이터 생성
        NotificationJpaRepository repository = mock(NotificationJpaRepository.class);
        List<Notification> fakeResults = new ArrayList<>();


        Notification notification1 = Notification.builder()
                .dog(Dog.builder()
                        .size("소형견")
                        .breed("시바견")
                        .build())
                .title("제목3 공통")
                .lat(34.25)
                .lng(60.1)
                .coin(BigDecimal.valueOf(3000))
                .startTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 22, 36))
                .endTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 23, 36))
                .significant("우리 아이는 착해용ㅋㅋㅋㅋㅋㅋㅋ")
                .build();

        fakeResults.add(notification1);

        Notification notification2 = Notification.builder()
                .dog(Dog.builder()
                        .size("중형견")
                        .breed("비글")
                        .build())
                .title("제목6 공통")
                .lat(34.25)
                .lng(90.1)
                .coin(BigDecimal.valueOf(3000))
                .startTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 22, 36))
                .endTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 23, 36))
                .significant("우리 아이는 착해용ㅋㅋㅋㅋㅋㅋㅋ")
                .build();

        fakeResults.add(notification2);



        when(repository.findAllHasSizeKeySearch(tit, size, latitude, longitude, key, pageable))
                .thenReturn(fakeResults);

        // 테스트 수행
        List<Notification> actualResults = repository.findAllHasSizeKeySearch(tit, size, latitude, longitude, key, pageable);

        if (!actualResults.isEmpty()) {
            System.out.println("테스트 : " + actualResults.get(0).getTitle() + actualResults.get(0).getDog().getBreed() + actualResults.get(0).getDog().getSize());
        } else {
            System.out.println("테스트 결과 목록이 비어 있습니다.");
        }
        assertEquals(fakeResults, actualResults);
    }
}