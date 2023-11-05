package com.kakaoseventeen.dogwalking.chat.repository;

import com.kakaoseventeen.dogwalking.chat.domain.ChatRoom;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    /**
     * 사용자가 참여하고 있는 모든 채팅방 리스트를 출력하는 쿼리문
     * @param appMemberId
     * @param notiMemberId
     * @return List ChatRoom
     */
    List<ChatRoom> findChatRoomsByAppMemberIdOrNotiMemberId(Member appMemberId, Member notiMemberId);
}
