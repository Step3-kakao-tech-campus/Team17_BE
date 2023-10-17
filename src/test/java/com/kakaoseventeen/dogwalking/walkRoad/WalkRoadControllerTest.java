package com.kakaoseventeen.dogwalking.walkRoad;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import com.kakaoseventeen.dogwalking.walk.repository.WalkRepository;
import com.kakaoseventeen.dogwalking.walkRoad.domain.WalkRoad;
import com.kakaoseventeen.dogwalking.walkRoad.dto.WalkRoadReqDTO;
import com.kakaoseventeen.dogwalking.walkRoad.repository.WalkRoadRepository;
import com.kakaoseventeen.dogwalking.chat.model.ChatRoom;
import com.kakaoseventeen.dogwalking.chat.repository.ChatRoomRepository;
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

import static com.kakaoseventeen.dogwalking.walk.WalkRestControllerTest.getMaster;
import static com.kakaoseventeen.dogwalking.walk.WalkRestControllerTest.getWalker;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
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
public class WalkRoadControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    WalkRoadRepository walkRoadRepository;

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

        Walk walk = Walk.of(master, walker, chatRoom1);
        walkRepository.saveAndFlush(walk);

        for (int i = 0; i < 8; i++){
            walkRoadRepository.saveAndFlush(WalkRoad.of(i, 123.12312312, walk));
        }
    }

    @Test
    void save_walkRoad_test() throws Exception {
        // given
        int walkId = 1;

        WalkRoadReqDTO walkRoadReqDTO = new WalkRoadReqDTO();
        walkRoadReqDTO.setLat(2.123123);
        walkRoadReqDTO.setLng(1.231123);

        String requestBody = om.writeValueAsString(walkRoadReqDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post(String.format("/api/walkRoad/%d", walkId))
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.lat").value(2.123123));
        resultActions.andExpect(jsonPath("$.response.lng").value(1.231123));
    }

    @Test
    void get_walkRoad_test() throws Exception {
        // given
        int walkId = 1;

        // when
        ResultActions resultActions = mvc.perform(
                get(String.format("/api/walkRoad/%d", walkId))
        );

        // console
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.walkRoadLatLngDTOS[0].lat").value(0.0));
    }
}
