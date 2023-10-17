package com.kakaoseventeen.dogwalking.notification.service;

import com.kakaoseventeen.dogwalking.dog.domain.Dog;
import com.kakaoseventeen.dogwalking.dog.repository.DogJpaRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.notification.dto.response.LoadDogResponseDTO;
import com.kakaoseventeen.dogwalking.notification.repository.NotificationJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @InjectMocks
    private NotificationService notificationService;

    @Mock
    private DogJpaRepository dogJpaRepository;

    @Mock
    private NotificationJpaRepository notificationJpaRepository;


    @Test
    void load_dogs() throws Exception {
        //given
        Member member1 = Member.builder()
                .nickname("닉네임")
                .email("mkwak1125@gmail.com")
                .password("kwak!6038")
                .dogBowl(55)
                .coin(BigDecimal.valueOf(3000))
                .build();

        Dog dog1 = Dog.builder()
                .breed("푸들")
                .name("강아지이름1")
                .sex("수컷")
                .size("대형견")
                .member(member1)
                .build();

        Dog dog2 = Dog.builder()
                .breed("말티즈")
                .name("강아지이름2")
                .sex("암컷")
                .size("소형견")
                .member(member1)
                .build();

        List<Dog> dogList = List.of(dog1, dog2);

        //when
        given(dogJpaRepository.findDogsByMemberId(any()))
                .willReturn(dogList);
        LoadDogResponseDTO responseDTO = notificationService.loadDog(member1);

        //then
        Assertions.assertEquals(dogList.get(0).getBreed(), "푸들");
    }


}