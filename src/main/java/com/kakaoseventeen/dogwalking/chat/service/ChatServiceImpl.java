package com.kakaoseventeen.dogwalking.chat.service;

import com.kakaoseventeen.dogwalking.chat.dto.ChatReqDTO;
import com.kakaoseventeen.dogwalking.chat.dto.ChatResDTO;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService{

    @Override
    public ChatResDTO save(ChatReqDTO chatReqDTO, Long roomId) {
        return null;
    }
}
