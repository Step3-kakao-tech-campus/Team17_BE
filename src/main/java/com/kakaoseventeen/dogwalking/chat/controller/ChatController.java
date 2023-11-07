package com.kakaoseventeen.dogwalking.chat.controller;

import com.kakaoseventeen.dogwalking.chat.dto.ChatReqDTO;
import com.kakaoseventeen.dogwalking.chat.dto.ChatResDTO;
import com.kakaoseventeen.dogwalking.chat.service.ChatMessageWriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatMessageWriteService chatMessageWriteService;

    /**
     * connect : /chat/connect
     * subscribe : /topic/chat-sub/{roomId}
     * send : /app/{roomId}
     * @param roomId
     * @param chatMessage
     * @return
     */
    @MessageMapping("/{roomId}")
    @SendTo("/topic/chat-sub/{roomId}")
    public ChatResDTO sendMessage(
            @DestinationVariable Long roomId,
            @Payload ChatReqDTO chatMessage
    ) {
        log.info("채팅 접속 완료");
        return chatMessageWriteService.save(chatMessage, roomId);
    }
}
