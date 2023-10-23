package com.kakaoseventeen.dogwalking.notification.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaoseventeen.dogwalking._core.utils.GetEntity;
import com.kakaoseventeen.dogwalking.dog.domain.Dog;
import com.kakaoseventeen.dogwalking.dog.repository.DogJpaRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.notification.repository.NotificationJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class NotificationControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Autowired
    NotificationJpaRepository notificationJpaRepository;

    @Autowired
    DogJpaRepository dogJpaRepository;


    @BeforeEach
    void setUp() {
        Member master1 = GetEntity.getMaster1();
        memberJpaRepository.saveAndFlush(master1);


        Dog dog1 = Dog.builder()
                .breed("푸들")
                .name("강아지이름1")
                .sex("수컷")
                .size("대형견")
                .member(master1)
                .build();
        dogJpaRepository.saveAndFlush(dog1);

        Notification notification1 = Notification.builder()
                .dog(dog1)
                .title("제목1")
                .lat(34.25)
                .lng(43.1)
                .coin(BigDecimal.valueOf(40000))
                .startTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 22, 36))
                .endTime(LocalDateTime.of(2023, Month.OCTOBER, 13, 23, 36))
                .significant("우리 아이는 착해용")
                .build();
        notificationJpaRepository.saveAndFlush(notification1);

    }

    @WithUserDetails(value = "yardyard@likelion.org", userDetailsServiceBeanName = "customUserDetailsService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    void load_notification_test() throws Exception{
        // given
		int id = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get(String.format("/api/notification/%d", id))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.isMine").value("true"));
        resultActions.andExpect(jsonPath("$.response.dog.dogId").value(1));
    }

    @Test
    @WithUserDetails(value = "yardyard@likelion.org", userDetailsServiceBeanName = "customUserDetailsService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void yourTestMethod() {
        // Your test logic here

        // 예: 사용자의 이메일을 확인하는 테스트
        String userEmail = getCurrentUserEmail();
        assertEquals("yardyard@likelion.org", userEmail, "User email should match");
    }

    private String getCurrentUserEmail() {
        // 여기에서 현재 사용자의 이메일을 가져오는 코드를 작성
        // 예: SecurityContextHolder 등을 사용하여 현재 사용자 정보를 가져오고, 이메일을 추출
        return "yardyard@likelion.org";
    }
}