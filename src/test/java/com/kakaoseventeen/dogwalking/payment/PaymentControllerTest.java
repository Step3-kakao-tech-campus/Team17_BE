package com.kakaoseventeen.dogwalking.payment;

import com.kakaoseventeen.dogwalking._core.utils.GetEntity;
import com.kakaoseventeen.dogwalking.application.domain.Application;
import com.kakaoseventeen.dogwalking.application.repository.ApplicationRepository;
import com.kakaoseventeen.dogwalking.chat.repository.ChatRoomRepository;
import com.kakaoseventeen.dogwalking.match.domain.Match;
import com.kakaoseventeen.dogwalking.match.repository.MatchingRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.notification.repository.NotificationJpaRepository;
import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import com.kakaoseventeen.dogwalking.walk.repository.WalkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    NotificationJpaRepository notificationJpaRepository;

    @Autowired
    MatchingRepository matchingRepository;

    @Autowired
    ChatRoomRepository chatRoomRepository;

    @Autowired
    WalkRepository walkRepository;

    @BeforeEach
    @Transactional
    void set_up(){
        // 생성을 위한 DBInit
        Member master1 = GetEntity.getMaster1();
        memberJpaRepository.saveAndFlush(master1);

        Member walker1 = GetEntity.getWalker1();
        memberJpaRepository.saveAndFlush(walker1);

        Notification notification = notificationJpaRepository.saveAndFlush(GetEntity.getNotification());

        Application application = applicationRepository.saveAndFlush(Application.builder()
                .appMemberId(walker1)
                .aboutMe("저는 이승건입니다.")
                .build());

        matchingRepository.saveAndFlush(Match.builder()
                .applicationId(application)
                .notificationId(notification)
                .build());

        walkRepository.saveAndFlush((Walk.of(master1, walker1, notification)));

    }

    @WithUserDetails(value = "yardyard@likelion.org", userDetailsServiceBeanName = "customUserDetailsService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    void get_payment_test() throws Exception {
        // given
        int matchingId = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get(String.format("/api/payment/%d", matchingId))
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
    }

    @WithUserDetails(value = "yardyard@likelion.org", userDetailsServiceBeanName = "customUserDetailsService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    void save_payment_test() throws Exception {
        // given
        int matchingId = 1;
        int walkId = 1;

        // when
        ResultActions resultActions = mvc.perform(
                post(String.format("/api/payment/%d/%d", matchingId, walkId))
        );

        // eye
        Member master = memberJpaRepository.findById(1L).get();
        Member walker = memberJpaRepository.findById(2L).get();

        System.out.println("견주에게 남아있는 돈은" + master.getCoin().toString());
        System.out.println("알바에게 남아있는 돈은" + walker.getCoin().toString());


        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
    }
}
