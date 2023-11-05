package com.kakaoseventeen.dogwalking.chat.controller;

import com.kakaoseventeen.dogwalking._core.utils.ApiResponse;
import com.kakaoseventeen.dogwalking._core.utils.ApiResponseGenerator;
import com.kakaoseventeen.dogwalking.chat.dto.ChatListResDTO;
import com.kakaoseventeen.dogwalking.chat.dto.ChatRoomReqDTO;
import com.kakaoseventeen.dogwalking.chat.service.ChatRoomReadService;
import com.kakaoseventeen.dogwalking.chat.service.ChatRoomWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomWriteService chatRoomWriteService;
    private final ChatRoomReadService chatRoomReadService;

    @PostMapping("/api/chatroom/create")
    public ApiResponse<?> createChatRoom(@RequestBody ChatRoomReqDTO chatRoomReqDTO){
        chatRoomWriteService.save(chatRoomReqDTO);

        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    @GetMapping("/api/chat/list")
    public ApiResponse<ApiResponse.CustomBody<List<ChatListResDTO>>> getChatList(){
        List<ChatListResDTO> response = chatRoomReadService.getChatList();

        return ApiResponseGenerator.success(response, HttpStatus.OK);
    }
}
