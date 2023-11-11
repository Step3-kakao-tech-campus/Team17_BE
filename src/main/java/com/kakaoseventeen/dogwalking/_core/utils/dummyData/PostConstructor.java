package com.kakaoseventeen.dogwalking._core.utils.dummyData;

import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PostConstructor {

    private final MemberRepository memberRepository;
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
        memberRepository.saveAll(List.of(member1, member2));


    }
}
