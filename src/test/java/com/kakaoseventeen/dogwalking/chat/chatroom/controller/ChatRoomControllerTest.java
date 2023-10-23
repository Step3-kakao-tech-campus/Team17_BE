package com.kakaoseventeen.dogwalking.chat.chatroom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaoseventeen.dogwalking._core.security.CustomUserDetailsService;
import com.kakaoseventeen.dogwalking._core.security.JwtProvider;
import com.kakaoseventeen.dogwalking._core.security.SecurityConfig;
import com.kakaoseventeen.dogwalking._core.utils.GlobalExceptionHandler;
import com.kakaoseventeen.dogwalking.chat.controller.ChatRoomController;
import com.kakaoseventeen.dogwalking.chat.dto.ChatRoomReqDTO;
import com.kakaoseventeen.dogwalking.chat.service.ChatRoomWriteService;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * ChatRoomControllerTest(채팅 방 컨트롤러 단위 테스트)
 *
 * @author 영규 박
 * @version 1.0
 */

@ActiveProfiles("test")
@WebMvcTest(controllers = ChatRoomController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Import({
        SecurityConfig.class,
        JwtProvider.class,
        GlobalExceptionHandler.class
})
public class ChatRoomControllerTest {

    @MockBean
    ChatRoomWriteService chatRoomWriteService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper om;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @DisplayName("채팅방 생성 테스트")
    @WithMockUser(username = "cookie@naver.com", roles = "ADMIN")
    @Test
    public void createChatRoom_test() throws Exception{
        // given
        ChatRoomReqDTO chatRoomReqDTO = ChatRoomReqDTO.builder()
                .appMemberId(1L)
                .notiMemberId(1L)
                .build();

        String requestBody = om.writeValueAsString(chatRoomReqDTO);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/chatroom/create")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("test : " + responseBody);

        // then
        resultActions.andExpectAll(
                MockMvcResultMatchers.jsonPath("$.success").value("true"),
                MockMvcResultMatchers.jsonPath("$.response").value(IsNull.nullValue()),
                MockMvcResultMatchers.jsonPath("$.error").value(IsNull.nullValue())
        );
    }
}
