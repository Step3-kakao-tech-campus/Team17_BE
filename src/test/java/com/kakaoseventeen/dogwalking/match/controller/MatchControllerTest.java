
package com.kakaoseventeen.dogwalking.match.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaoseventeen.dogwalking.application.domain.Application;
import com.kakaoseventeen.dogwalking.application.repository.ApplicationRepository;
import com.kakaoseventeen.dogwalking.dog.domain.Dog;
import com.kakaoseventeen.dogwalking.dog.repository.DogRepository;
import com.kakaoseventeen.dogwalking.match.domain.Match;
import com.kakaoseventeen.dogwalking.match.repository.MatchRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberRepository;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.notification.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.Month;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * 매칭리스트 Presentation Layer 테스트
 *
 * @author 곽민주
 * @version 1.0
 */
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class MatchControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper om;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private DogRepository dogRepository;


    @BeforeEach
    void set_up(){
        Member member1 = memberRepository.save(Member.builder()
                .nickname("닉네임1")
                .email("mkwak1125@gmail.com")
                .password(passwordEncoder.encode("kwak!6038"))
                .profileImage("1번 이미지")
                .profileContent("나는 1번 멤버")
                .coin(BigDecimal.valueOf(100000))
                .build());

        Dog dog1 = Dog.builder()
                .breed("푸들")
                .name("강아지이름1")
                .sex("수컷")
                .size("대형견")
                .specificity("알러지있음")
                .image("1번 강아지 이미지")
                .age(3)
                .member(member1)
                .build();
        dogRepository.saveAndFlush(dog1);

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
        notificationRepository.saveAndFlush(notification1);

        Application application1 = applicationRepository.saveAndFlush(Application.builder()
                .aboutMe("저에 관해서 소개를 하겠습니다. 1번 지원자")
                .title("지원서 제목1")
                .appMemberId(member1)
                .certification("애견 보호사 2급")
                .experience("강아지 유치원 2년 근무")
                .build());

        matchRepository.saveAndFlush(Match.builder()
                .notificationId(notification1)
                .applicationId(application1)
                .build());
    }


    @DisplayName("매칭 리스트 실패 출력")
    @Test
    @WithUserDetails(value = "mkwak1125@gmail.com", userDetailsServiceBeanName = "customUserDetailsService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void findMatchTest() throws Exception {
        // given
		int notificationId = 1;
        // when

        ResultActions resultActions = mvc.perform(
                get(String.format("/api/notification/%d/match", notificationId))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        String responseBody = new String(resultActions.andReturn().getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);
        System.out.println("테스트 : " + responseBody);

        resultActions.andExpect(jsonPath("$.success").value("false"));
    }


    @DisplayName("매칭 리스트 출력 성공 테스트 ")
    @Test
    @WithUserDetails(value = "mkwak1125@gmail.com", userDetailsServiceBeanName = "customUserDetailsService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void findMatchFailTest() throws Exception {
        // given
        int notificationId = 2;
        // when

        ResultActions resultActions = mvc.perform(
                get(String.format("/api/notification/%d/match", notificationId))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        String responseBody = new String(resultActions.andReturn().getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);
        System.out.println("테스트 : " + responseBody);

        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.error.message").value("해당 공고글이 존재하지 않습니다."));
    }


}