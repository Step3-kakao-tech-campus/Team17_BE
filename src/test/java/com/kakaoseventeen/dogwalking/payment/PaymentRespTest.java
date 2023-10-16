package com.kakaoseventeen.dogwalking.payment;

import com.kakaoseventeen.dogwalking._core.payment.domain.Payment;
import com.kakaoseventeen.dogwalking._core.payment.repository.PaymentRepository;
import com.kakaoseventeen.dogwalking._core.walk.domain.Walk;
import com.kakaoseventeen.dogwalking._core.walk.repository.WalkRepository;
import com.kakaoseventeen.dogwalking.chat.model.ChatRoom;
import com.kakaoseventeen.dogwalking.chat.repository.ChatRoomRepository;
import com.kakaoseventeen.dogwalking.dog.domain.Dog;
import com.kakaoseventeen.dogwalking.dog.repository.DogJpaRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.notification.repository.NotificationJpaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@DataJpaTest
public class PaymentRespTest {

    @Autowired
    PaymentRepository repository;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Autowired
    DogJpaRepository dogJpaRepository;

    @Autowired
    WalkRepository walkRepository;

    @Autowired
    NotificationJpaRepository notificationJpaRepository;

    @Autowired
    ChatRoomRepository chatRoomRepository;

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

        ChatRoom chatRoom = new ChatRoom(member, member);
        chatRoomRepository.saveAndFlush(chatRoom);

        Walk walk1 = Walk.of(member, member, chatRoom);
        walkRepository.saveAndFlush(walk1);

        Payment payment = Payment.from(walk1);
        repository.saveAndFlush(payment);

        Notification notification =
                Notification.builder()
                        .lng(3.12312312)
                        .lat(2.2312312)
                        .title("산책 구합니다~")
                        .startTime(LocalDateTime.now())
                        .endTime(LocalDateTime.MAX)
                        .significant("울 강아지는 물어여")
                        .coin(BigDecimal.valueOf(20000))
                        .dog(dog)
                        .build();

        notificationJpaRepository.saveAndFlush(notification);
    }

    /**
     *  paymentId를 통해서 Walk 엔티티의 Dog 엔티티를 fetch join으로 함께 가져오는 쿼리 테스트
     */
    @Test
    void test_1(){
        // given
        Long paymentId = 1L;

        // when
        //Walk walk = repository.findByWalkWithPaymentId(paymentId).get();

        // then
        //Assertions.assertEquals(walk.getDog().getName(), "복슬이");
    }

    /**
     *  dogId를 통해서 Notification 엔티티와 Dog 엔티티를 fetch join으로 가져오는 쿼리 테스트
     */
    @Test
    void test_2(){
        // given
        Long paymentId = 1L;

        //Walk walk = repository.findByWalkWithPaymentId(paymentId).get();

        // when
        //Notification notification = notificationJpaRepository.findByNotificationWithDogId(walk.getDog().getId()).get();

        // then
        //Assertions.assertEquals(notification.getDog().getName(), "복슬이");
    }
}
