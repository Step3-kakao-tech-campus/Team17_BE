package com.kakaoseventeen.dogwalking._core.utils.dummyData;


import com.kakaoseventeen.dogwalking.application.domain.Application;
import com.kakaoseventeen.dogwalking.application.repository.ApplicationRepository;
import com.kakaoseventeen.dogwalking.chat.domain.ChatRoom;
import com.kakaoseventeen.dogwalking.chat.repository.ChatRoomRepository;
import com.kakaoseventeen.dogwalking.dog.domain.Dog;
import com.kakaoseventeen.dogwalking.dog.repository.DogJpaRepository;
import com.kakaoseventeen.dogwalking.match.domain.Match;
import com.kakaoseventeen.dogwalking.match.repository.MatchingRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.notification.repository.NotificationJpaRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PostConstructor {

    private final MemberJpaRepository memberJpaRepository;
    private final DogJpaRepository dogJpaRepository;
    private final NotificationJpaRepository notificationJpaRepository;
    private final ApplicationRepository applicationRepository;
    private final MatchingRepository matchingRepository;
    private final ChatRoomRepository chatRoomRepository;

    @PostConstruct
    public void init(){
        Member member1 = Member.builder() // 공고자
                .id(1L)
                .nickname("test1")
                .email("test1@naver.com")
                .password("test1")
                .build();
        Member member2 = Member.builder() // 지원자
                .id(2L)
                .nickname("test2")
                .email("test2@naver.com")
                .password("test2")
                .build();
        memberJpaRepository.saveAll(List.of(member1, member2));

        Dog dog = Dog.builder()
                .name("테스트 개이름")
                .sex("수컷")
                .breed("testBreed")
                .size("testSize")
                .member(member1)
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

        Application application = Application.builder()
                .applicationId(1L)
                .title("지원서 제목")
                .aboutMe("나에 대해서")
                .appMemberId(member2)
                .build();
        applicationRepository.save(application);

        Match match = Match.builder()
                .matchId(1L)
                .notificationId(notification)
                .applicationId(application)
                .isSuccess(true)
                .build();
        matchingRepository.save(match);

        ChatRoom chatRoom = ChatRoom.builder()
                .matchId(match)
                .notiMemberId(member1)
                .appMemberId(member2)
                .build();
        chatRoomRepository.save(chatRoom);
    }
}
