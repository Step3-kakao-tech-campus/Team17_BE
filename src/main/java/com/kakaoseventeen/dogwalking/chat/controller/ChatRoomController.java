package com.kakaoseventeen.dogwalking.chat.controller;

import com.kakaoseventeen.dogwalking._core.utils.ApiResponse;
import com.kakaoseventeen.dogwalking._core.utils.ApiResponseGenerator;
import com.kakaoseventeen.dogwalking.chat.dto.ChatRoomReqDTO;
import com.kakaoseventeen.dogwalking.chat.service.ChatRoomWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomWriteService chatRoomWriteService;

    @PostMapping("/api/chatroom/create")
    public ApiResponse<?> createChatRoom(@RequestBody ChatRoomReqDTO chatRoomReqDTO){
        chatRoomWriteService.save(chatRoomReqDTO);

        return ApiResponseGenerator.success(HttpStatus.OK);
    }
}
