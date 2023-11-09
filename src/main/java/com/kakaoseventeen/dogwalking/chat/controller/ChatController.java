package com.kakaoseventeen.dogwalking.chat.controller;

import com.kakaoseventeen.dogwalking._core.utils.ApiResponse;
import com.kakaoseventeen.dogwalking._core.utils.ApiResponseGenerator;
import com.kakaoseventeen.dogwalking.chat.dto.ChatMessageResDTO;
import com.kakaoseventeen.dogwalking.chat.dto.ChatReqDTO;
import com.kakaoseventeen.dogwalking.chat.dto.ChatResDTO;
import com.kakaoseventeen.dogwalking.chat.service.ChatMessageReadService;
import com.kakaoseventeen.dogwalking.chat.service.ChatMessageWriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatMessageWriteService chatMessageWriteService;
    private final ChatMessageReadService chatMessageReadService;

    /**
     * connect : /chat/connect
     * subscribe : /topic/chat-sub/{roomId}
     * send : /app/{roomId}
     * @param roomId is PK ChatRoom table
     * @param chatMessage ChatReqDTO.class
     * @return
     */
    @MessageMapping("/{roomId}")
    @SendTo("/api/topic/chat-sub/{roomId}")
    public ChatResDTO sendMessage(
            @DestinationVariable Long roomId,
            @Payload ChatReqDTO chatMessage
    ) {
        log.info("채팅 접속 완료");
        return chatMessageWriteService.save(chatMessage, roomId);
    }

    @GetMapping("/api/chat/{chatRoomId}")
    public ApiResponse<ApiResponse.CustomBody<List<ChatMessageResDTO>>> getMessage(@PathVariable Long chatRoomId){
        List<ChatMessageResDTO> response = chatMessageReadService.getMessage(chatRoomId);

        return ApiResponseGenerator.success(response, HttpStatus.OK);
    }


}
