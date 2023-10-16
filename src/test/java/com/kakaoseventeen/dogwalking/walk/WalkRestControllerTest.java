package com.kakaoseventeen.dogwalking.walk;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;

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
    ChatRoomRepository chatRoomRepository;

    @Autowired
    WalkRepository walkRepository;

    @BeforeEach
    void set_up(){
        Member master = getMaster();
        memberJpaRepository.saveAndFlush(master);

        Member walker = getWalker();
        memberJpaRepository.saveAndFlush(walker);

        ChatRoom chatRoom1 = new ChatRoom(master, walker);
        chatRoomRepository.saveAndFlush(chatRoom1);

        ChatRoom chatRoom2 = new ChatRoom(master, walker);
        chatRoomRepository.saveAndFlush(chatRoom2);

        Walk walk = Walk.of(master, walker, chatRoom1);
        walkRepository.saveAndFlush(walk);
    }

    @Test
    void accept_walk_test() throws Exception {
        // given
        int masterId = 1;
        int walkerId = 2;
        int chatRoomId = 2;

        // when
        ResultActions resultActions = mvc.perform(
                post(String.format("/api/walk/%d/%d/%d", masterId, walkerId, chatRoomId))
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
        int chatRoomId = 1;

        // when
        ResultActions resultActions = mvc.perform(
                post(String.format("/api/walk/start/%d", chatRoomId))
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.walkStatus").value("ACTIVATE"));
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

    public static Member getMaster(){
        return Member.builder()
                .email("yardyard@likelion.org")
                .password("asd1111")
                .profileContent("반갑다리요")
                .dogBowl(50)
                .nickname("견주유저1")
                .coin(BigDecimal.valueOf(3000))
                .build();
    }

    public static Member getWalker(){
        return Member.builder()
                .email("yardyard@naver.com")
                .password("asd1111")
                .profileContent("반갑다리요")
                .dogBowl(50)
                .nickname("알바유저1")
                .coin(BigDecimal.valueOf(3000))
                .build();
    }
}
