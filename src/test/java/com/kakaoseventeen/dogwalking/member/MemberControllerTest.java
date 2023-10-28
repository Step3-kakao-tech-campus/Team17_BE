package com.kakaoseventeen.dogwalking.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaoseventeen.dogwalking._core.utils.GetEntity;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.dto.LoginRequestDTO;
import com.kakaoseventeen.dogwalking.member.dto.UpdateProfileReqDTO;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;

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

        LoginRequestDTO reqDTO = new LoginRequestDTO();
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
    @Test
    void save_walkRoad_test() throws Exception {
        // given
        int userId = 1;

        UpdateProfileReqDTO reqDTO = new UpdateProfileReqDTO();
        reqDTO.setProfileContent("변경되었다리");
        reqDTO.setProfileImage("changedImage");

        String requestBody = om.writeValueAsString(reqDTO);


        // when
        ResultActions resultActions = mvc.perform(
                post(String.format("/api/profile/user/%d", userId))
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // console
        String responseBody = new String(resultActions.andReturn().getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.profileImage").value("changedImage"));
        resultActions.andExpect(jsonPath("$.response.profileContent").value("변경되었다리"));
    }
}
