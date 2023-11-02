package com.kakaoseventeen.dogwalking.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaoseventeen.dogwalking._core.utils.GetEntity;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.dto.LoginReqDTO;
import com.kakaoseventeen.dogwalking.member.dto.UpdateProfileReqDTO;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;
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

import java.nio.charset.StandardCharsets;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * MemberControllerTest(유저 통합 테스트)
 *
 * @author 승건 이
 * @version 1.0
 */
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class MemberControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @BeforeEach
    void set_up(){
        Member master = GetEntity.getMaster1();
        memberJpaRepository.saveAndFlush(master);
    }

    @Test
    void login_success_test() throws Exception {
        // given

        LoginReqDTO reqDTO = new LoginReqDTO();
        reqDTO.setEmail("mkwak1125@gmail.com");
        reqDTO.setPassword("kwak!6038");

        String requestBody = om.writeValueAsString(reqDTO);

        ResultActions resultActions = mvc.perform(
                post(String.format("/api/member/login"))
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        resultActions.andExpect(jsonPath("$.success").value("true"));

    }

    @WithUserDetails(value = "yardyard@likelion.org", userDetailsServiceBeanName = "customUserDetailsService", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    void save_walkRoad_test() throws Exception {
        // given
        long userId = 1;

        // when
        mvc.perform(
                get("/init")
        );

        ResultActions resultActions = mvc.perform(
                get(String.format("/api/profile/%d", userId))
        );

        // console
        String responseBody = new String(resultActions.andReturn().getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
    }
}
