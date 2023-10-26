package com.kakaoseventeen.dogwalking.chat.controller;

import com.kakaoseventeen.dogwalking.chat.dto.ChatReqDTO;
import com.kakaoseventeen.dogwalking.chat.dto.ChatResDTO;
import com.kakaoseventeen.dogwalking.chat.service.ChatMessageWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageWriteService chatMessageWriteService;

    @MessageMapping("/{roomId}")
    @SendTo("/chat-sub/public")
    public ChatResDTO sendMessage(
            @DestinationVariable Long roomId,
            @Payload ChatReqDTO chatMessage
    ) {

        return chatMessageWriteService.save(chatMessage, roomId);
    }

}
