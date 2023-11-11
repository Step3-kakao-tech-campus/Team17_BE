package com.kakaoseventeen.dogwalking.chat.service;

import com.kakaoseventeen.dogwalking.chat.dto.ChatReqDTO;
import com.kakaoseventeen.dogwalking.chat.dto.ChatResDTO;

public interface ChatMessageWriteService {

    ChatResDTO save(ChatReqDTO chatReqDTO, Long roomId);
}
