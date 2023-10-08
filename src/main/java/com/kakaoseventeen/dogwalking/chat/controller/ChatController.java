package com.kakaoseventeen.dogwalking.chat.controller;

import com.kakaoseventeen.dogwalking.chat.dto.ChatReqDTO;
import com.kakaoseventeen.dogwalking.chat.dto.ChatResDTO;
import com.kakaoseventeen.dogwalking.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/{roomId}")
    @SendTo("/chat/sub/{roomId}")
    public ChatResDTO sendMessage(@DestinationVariable Long roomId,
                                  @Payload ChatReqDTO chatReqDTO){
        return chatService.save(chatReqDTO, roomId);
    }
}
