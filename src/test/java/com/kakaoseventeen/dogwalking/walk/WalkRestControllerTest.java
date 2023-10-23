package com.kakaoseventeen.dogwalking.walk;

import com.kakaoseventeen.dogwalking._core.utils.GetEntity;
import com.kakaoseventeen.dogwalking.application.domain.Application;
import com.kakaoseventeen.dogwalking.application.repository.ApplicationRepository;
import com.kakaoseventeen.dogwalking.match.domain.Match;
import com.kakaoseventeen.dogwalking.match.repository.MatchingRepository;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.notification.repository.NotificationJpaRepository;
import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import com.kakaoseventeen.dogwalking.walk.repository.WalkRepository;
import com.kakaoseventeen.dogwalking.chat.repository.ChatRoomRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;

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

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * WalkControllerTest(산책 통합 테스트)
 *
 * @author 승건 이
 * @version 1.0
 */
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class WalkRestControllerTest {

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

        // 조회를 위한 DBInit
        Member master2 = memberJpaRepository.saveAndFlush(GetEntity.getMaster2());

        Member walker2 = memberJpaRepository.saveAndFlush(GetEntity.getWalker2());

        Notification notification2 = notificationJpaRepository.saveAndFlush(GetEntity.getNotification2());

        walkRepository.saveAndFlush((Walk.of(master2, walker2, notification2)));

        Application application2 = applicationRepository.saveAndFlush(Application.builder()
                .appMemberId(walker2)
                .aboutMe("저는 이승건입니다.")
                .build());

        matchingRepository.saveAndFlush(Match.builder()
                .applicationId(application2)
                .notificationId(notification2)
                .build());
    }

    @WithUserDetails(value = "yardyard@likelion.org", userDetailsServiceBeanName = "customUserDetailsService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    void accept_walk_test() throws Exception {
        // given

        int walkerId = 2;
        int matchingId = 1;

        // when
        ResultActions resultActions = mvc.perform(
                post(String.format("/api/walk/%d/%d", walkerId, matchingId))
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
    }

    @Test
    void start_walk_test() throws Exception {
        // given
        int matchId = 2;

        // when
        ResultActions resultActions = mvc.perform(
                post(String.format("/api/walk/start/%d", matchId))
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
    }

    @Test
    @WithUserDetails(value = "yardyard@likelion.org", userDetailsServiceBeanName = "customUserDetailsService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void end_walk_test() throws Exception {
        // given
        int matchId = 2;

        // when
        ResultActions resultActions = mvc.perform(
                post(String.format("/api/walk/end/%d", matchId))
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
    }
}
