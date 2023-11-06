package com.kakaoseventeen.dogwalking.notification.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class HomeControllerTest {
    @Autowired
    private MockMvc mvc;

    @DisplayName("홈 메인화면 테스트 - 견종, 강아지 사이즈 필터 ")
    @Test
    void get_post_test1() throws Exception {
        // given
        Double lat = 0.0;
        Double lng = 0.0;
        List<String> big = new ArrayList<>();
        big.add("소형견");
        big.add("중형견");
        String breed = "푸들";


        // when
        mvc.perform(
                get("/init")
        );

        ResultActions resultActions = mvc.perform(
                get(String.format("/api/home"))
                        .param("latitude",lat.toString())
                        .param("longitude",lng.toString())
                        .param("big",big.get(0))
                        .param("big",big.get(1))
                        .param("breed",breed)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        String responseBody = new String(resultActions.andReturn().getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);
        System.out.println("테스트 : " + responseBody);

        resultActions.andExpect(jsonPath("$.success").value("true"));
    }

    @DisplayName("홈 메인화면 테스트 - 제목 키워드")
    @Test
    void get_post_test2() throws Exception {
        // given
        Double lat = 0.0;
        Double lng = 0.0;
        String keyword = "공통";


        // when
        mvc.perform(
                get("/init")
        );

        ResultActions resultActions = mvc.perform(
                get(String.format("/api/home"))
                        .param("latitude",lat.toString())
                        .param("longitude",lng.toString())
                        .param("word", keyword)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        String responseBody = new String(resultActions.andReturn().getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);
        System.out.println("테스트 : " + responseBody);

        resultActions.andExpect(jsonPath("$.success").value("true"));
    }



    @DisplayName("홈 메인화면 테스트 - 견종, 강아지 크기, 제목 키워드")
    @Test
    void get_post_test3() throws Exception {
        // given
        Double lat = 0.0;
        Double lng = 0.0;
        List<String> big = new ArrayList<>();
        big.add("소형견");
        big.add("중형견");
        String breed = "푸들";
        String keyword = "공통";


        // when
        mvc.perform(
                get("/init")
        );

        ResultActions resultActions = mvc.perform(
                get(String.format("/api/home"))
                        .param("latitude",lat.toString())
                        .param("longitude",lng.toString())
                        .param("big",big.get(0))
                        .param("big",big.get(1))
                        .param("breed",breed)
                        .param("word", keyword)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // then
        String responseBody = new String(resultActions.andReturn().getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);
        System.out.println("테스트 : " + responseBody);

        resultActions.andExpect(jsonPath("$.success").value("true"));
    }


}