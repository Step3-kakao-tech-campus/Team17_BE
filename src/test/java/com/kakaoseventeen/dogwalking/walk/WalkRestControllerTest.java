package com.kakaoseventeen.dogwalking.walk;

import com.kakaoseventeen.dogwalking._core.security.CustomUserDetailsService;
import com.kakaoseventeen.dogwalking._core.utils.GetEntity;
import com.kakaoseventeen.dogwalking.application.domain.Application;
import com.kakaoseventeen.dogwalking.application.repository.ApplicationRepository;
import com.kakaoseventeen.dogwalking.match.domain.Match;
import com.kakaoseventeen.dogwalking.match.repository.MatchingRepository;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.notification.repository.NotificationJpaRepository;
import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import com.kakaoseventeen.dogwalking.walk.repository.WalkRepository;
import com.kakaoseventeen.dogwalking.chat.model.ChatRoom;
import com.kakaoseventeen.dogwalking.chat.repository.ChatRoomRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
        Member master = GetEntity.getMaster();
        memberJpaRepository.saveAndFlush(master);

        Member walker = GetEntity.getWalker();
        memberJpaRepository.saveAndFlush(walker);

        Notification notification = notificationJpaRepository.saveAndFlush(GetEntity.getNotification());

//        Walk walk1 = Walk.of(master, walker, notification);
//        walkRepository.saveAndFlush(walk1);

        Application application = applicationRepository.saveAndFlush(Application.builder()
                .appMemberId(walker)
                .aboutMe("저는 이승건입니다.")
                .build());

        Match match = Match.builder()
                .applicationId(application)
                .notificationId(notification)
                .build();

        matchingRepository.saveAndFlush(match);
    }

    @WithUserDetails(value = "yardyard@likelion.org", userDetailsServiceBeanName = "customUserDetailsService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    void accept_walk_test() throws Exception {
        // given
        int masterId = 1;
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
    @Transactional
    void start_walk_test() throws Exception {
        // given
        int matchId = 1;
        Member master = memberJpaRepository.findById(1L).get();
        Member walker = memberJpaRepository.findById(2L).get();
        Notification notification = notificationJpaRepository.findById(1L).get();
        Walk walk1 = Walk.of(master, walker, notification);
        walkRepository.saveAndFlush(walk1);

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
    void end_walk_test() throws Exception {
        // given
        int chatRoomId = 1;

        // when
        ResultActions resultActions = mvc.perform(
                post(String.format("/api/walk/end/%d", chatRoomId))
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.walkStatus").value("END"));
    }

}
