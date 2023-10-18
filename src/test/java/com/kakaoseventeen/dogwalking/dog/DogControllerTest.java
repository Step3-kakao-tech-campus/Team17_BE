package com.kakaoseventeen.dogwalking.dog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaoseventeen.dogwalking._core.utils.GetEntity;
import com.kakaoseventeen.dogwalking.dog.domain.Dog;
import com.kakaoseventeen.dogwalking.dog.dto.DogReqDTO;
import com.kakaoseventeen.dogwalking.dog.repository.DogJpaRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
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

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * DogControllerTest(애완견 통합 테스트)
 *
 * @author 승건 이
 * @version 1.0
 */
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DogControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    DogJpaRepository dogJpaRepository;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @BeforeEach
    void set_up(){
        Member master = GetEntity.getWalker1();
        memberJpaRepository.saveAndFlush(master);

        DogReqDTO dogReqDTO = DogReqDTO.builder()
                .age(2)
                .breed("요크")
                .image("img")
                .name("복슬")
                .sex("MALE")
                .size("대형")
                .specificity("안물어요")
                .build();

        dogJpaRepository.saveAndFlush(Dog.of(dogReqDTO, master));
    }

    @Test
    void save_dog_test() throws Exception {
        // given
        int masterId = 1;

        DogReqDTO dogReqDTO = DogReqDTO.builder()
                .age(2)
                .breed("요크")
                .image("img")
                .name("복슬")
                .sex("MALE")
                .size("대형")
                .specificity("안물어요")
                .build();

        String requestBody = om.writeValueAsString(dogReqDTO);


        // when
        ResultActions resultActions = mvc.perform(
                post(String.format("/api/profile/dog/%d", masterId))
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // console
        String responseBody = new String(resultActions.andReturn().getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.dogId").value(2));
    }

    @Test
    void find_dog_test() throws Exception {
        // given
        int dogId = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get(String.format("/api/profile/dog/%d", dogId))
        );

        // console
        String responseBody = new String(resultActions.andReturn().getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
    }
}
