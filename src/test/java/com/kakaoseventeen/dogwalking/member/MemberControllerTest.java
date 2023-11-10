package com.kakaoseventeen.dogwalking.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.dto.LoginReqDTO;
import com.kakaoseventeen.dogwalking.member.dto.SignupReqDTO;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;
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
import java.nio.charset.StandardCharsets;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * MemberControllerTest(유저 통합 테스트)
 * 회원가입과 로그인 성공 및 실패 테스트
 *
 * @author 승건 이, 박영규
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 로그인 성공 테스트
     * @status 200
     */
    @DisplayName("로그인 성공 테스트")
    @Test
    void login_success_test() throws Exception {
        // given

        // when
        mvc.perform(
                get("/init")
        );

        LoginReqDTO reqDTO = new LoginReqDTO();
        reqDTO.setEmail("mkwak1125@gmail.com");
        reqDTO.setPassword("kwak!6038");

        String requestBody = om.writeValueAsString(reqDTO);

        ResultActions resultActions = mvc.perform(
                post(String.format("/api/member/login"))
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = new String(resultActions.andReturn().getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);
        System.out.println("테스트 : " + responseBody);

        resultActions.andExpect(jsonPath("$.success").value("true"));

    }

    @DisplayName("로그인 실패 테스트 - 비밀번호가 틀렸을 경우")
    @Test
    void login_fail_test() throws Exception {
        // given

        // when
        mvc.perform(
                get("/init")
        );

        LoginReqDTO reqDTO = new LoginReqDTO();
        reqDTO.setEmail("mkwak1125@gmail.com");
        reqDTO.setPassword("kwak!60389");

        String requestBody = om.writeValueAsString(reqDTO);

        ResultActions resultActions = mvc.perform(
                post(String.format("/api/member/login"))
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = new String(resultActions.andReturn().getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);
        System.out.println("테스트 : " + responseBody);

        resultActions.andExpect(jsonPath("$.success").value("false"));

    }

    @DisplayName("로그인 실패 테스트 - 이메일 형식 안맞을 경우")
    @Test
    void login_email_fail_test() throws Exception {
        // given

        // when
        mvc.perform(
                get("/init")
        );

        LoginReqDTO reqDTO = new LoginReqDTO();
        reqDTO.setEmail("mkwak1125gmail.com");
        reqDTO.setPassword("kwak!6038");

        String requestBody = om.writeValueAsString(reqDTO);

        ResultActions resultActions = mvc.perform(
                post(String.format("/api/member/login"))
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = new String(resultActions.andReturn().getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);
        System.out.println("테스트 : " + responseBody);

        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("이메일 형식으로 작성해주세요."));

    }

    @DisplayName("로그인 실패 테스트 - 비밀번호 형식 안맞을 경우")
    @Test
    void login_password_fail_test() throws Exception {
        // given

        // when
        mvc.perform(
                get("/init")
        );

        LoginReqDTO reqDTO = new LoginReqDTO();
        reqDTO.setEmail("mkwak1125@gmail.com");
        reqDTO.setPassword("kwak6038");

        String requestBody = om.writeValueAsString(reqDTO);

        ResultActions resultActions = mvc.perform(
                post(String.format("/api/member/login"))
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = new String(resultActions.andReturn().getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);
        System.out.println("테스트 : " + responseBody);

        resultActions.andExpect(jsonPath("$.success").value("false"));
        resultActions.andExpect(jsonPath("$.error.message").value("비밀번호는 영문, 숫자, 특수문자를 포함하여 8~20자 이내여야 합니다."));
    }

    @DisplayName("로그인 실패 테스트 - 회원가입이 안되어있는 유저일 경우")
    @Test
    void not_joined_fail_test() throws Exception {
        // given

        // when
        mvc.perform(
                get("/init")
        );

        LoginReqDTO reqDTO = new LoginReqDTO();
        reqDTO.setEmail("mkwak11256@gmail.com");
        reqDTO.setPassword("kwak6038");

        String requestBody = om.writeValueAsString(reqDTO);

        ResultActions resultActions = mvc.perform(
                post(String.format("/api/member/login"))
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        String responseBody = new String(resultActions.andReturn().getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);
        System.out.println("테스트 : " + responseBody);

        resultActions.andExpect(jsonPath("$.success").value("false"));
    }

}
