package com.kakaoseventeen.dogwalking.walk.service;

import com.kakaoseventeen.dogwalking.chat.domain.ChatRoom;
import com.kakaoseventeen.dogwalking.chat.repository.ChatRoomRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;
import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import com.kakaoseventeen.dogwalking.walk.dto.WalkRespDTO;
import com.kakaoseventeen.dogwalking.walk.repository.WalkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Walk(산책) 서비스
 *
 * @author 승건 이
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class WalkService {

    private final WalkRepository walkRepository;

    private final MemberJpaRepository memberJpaRepository;

    private final ChatRoomRepository chatRoomRepository;

    /**
     * 산책 허락하기 메서드
     */
    @Transactional
    public void saveWalk(long masterId, long walkerId, Long chatRoomId) throws RuntimeException{
        Optional<Member> master = memberJpaRepository.findById(masterId);
        Optional<Member> walker = memberJpaRepository.findById(walkerId);
        Optional<ChatRoom> chatRoom = chatRoomRepository.findById(chatRoomId);

        if (chatRoom.isEmpty()) {
            throw new RuntimeException("올바르지 않은 채팅방 Id입니다.");
        }

        if (master.isPresent() && walker.isPresent()) {
            walkRepository.save(Walk.of(master.get(), walker.get(), chatRoom.get()));
        }

        else {
            throw new RuntimeException("올바르지 않은 멤버 Id입니다.");
        }
    }

    /**
     * 산책 시작하기 메서드
     */
    @Transactional
    public Walk startWalk(Long chatRoomId) throws RuntimeException{
        Optional<Walk> walk = walkRepository.findWalkByChatRoomId(chatRoomId);

        if (walk.isPresent()) {
            Walk walkOP = walk.get();
            walkOP.startWalk();
            return walkOP;
        }

        else {
            throw new RuntimeException("올바르지 않은 채팅방 Id입니다.");
        }
    }

    /**
     * 산책 종료하기 메서드
     */
    @Transactional
    public void terminateWalk(Long chatRoomId) {
        Optional<Walk> walk = walkRepository.findWalkByChatRoomId(chatRoomId);

        if (walk.isPresent()) {
            Walk walkOP = walk.get();
            walkOP.endWalk();
        }

        else {
            throw new RuntimeException("올바르지 않은 채팅방 Id입니다.");
        }
    }

    /**
     * userId를 통한 산책 조회 메서드
     */
    @Transactional(readOnly = true)
    public WalkRespDTO.FindByUserId findAllWalkStatusByUserId(long userId) throws RuntimeException{
        Optional<Member> member = memberJpaRepository.findById(userId);

        if (member.isEmpty()) {
            throw new RuntimeException("올바르지 않은 멤버 Id입니다.");
        }

        List<Walk> walks = walkRepository.findByWalkWithUserIdAndEndStatus(userId);

        return new WalkRespDTO.FindByUserId(walks);
    }
}

