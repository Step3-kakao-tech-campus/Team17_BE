package com.kakaoseventeen.dogwalking.chat.service;

import com.kakaoseventeen.dogwalking.chat.dto.ChatReqDTO;
import com.kakaoseventeen.dogwalking.chat.dto.ChatResDTO;

// 채팅 서비스는 추후 로직이 바뀔 수 있어서 interface 생성.
public interface ChatService {

    ChatResDTO save(ChatReqDTO chatReqDTO, Long roomId);
}
