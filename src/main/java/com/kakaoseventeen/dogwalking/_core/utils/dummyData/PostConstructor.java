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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PostConstructor {

    private final MemberJpaRepository memberJpaRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init(){
        Member member1 = Member.builder() // 공고자
                .id(1L)
                .nickname("test1")
                .email("test1@naver.com")
                .password(passwordEncoder.encode("test1234!"))
                .build();
        Member member2 = Member.builder() // 지원자
                .id(2L)
                .nickname("test2")
                .email("test2@naver.com")
                .password(passwordEncoder.encode("test1234!"))
                .build();
        memberJpaRepository.saveAll(List.of(member1, member2));


    }
}
