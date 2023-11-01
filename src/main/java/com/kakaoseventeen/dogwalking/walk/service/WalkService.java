package com.kakaoseventeen.dogwalking.walk.service;

import com.kakaoseventeen.dogwalking._core.security.CustomUserDetails;
import com.kakaoseventeen.dogwalking._core.utils.MemberMessageCode;
import com.kakaoseventeen.dogwalking._core.utils.MessageCode;
import com.kakaoseventeen.dogwalking._core.utils.exception.MatchNotExistException;
import com.kakaoseventeen.dogwalking._core.utils.exception.MemberNotExistException;
import com.kakaoseventeen.dogwalking._core.utils.exception.WalkNotExistException;
import com.kakaoseventeen.dogwalking.match.domain.Match;
import com.kakaoseventeen.dogwalking.match.repository.MatchingRepository;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.payment.domain.Payment;
import com.kakaoseventeen.dogwalking.payment.repository.PaymentRepository;
import com.kakaoseventeen.dogwalking.payment.service.PaymentService;
import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import com.kakaoseventeen.dogwalking.walk.dto.WalkRespDTO;
import com.kakaoseventeen.dogwalking.walk.repository.WalkRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.kakaoseventeen.dogwalking._core.utils.MemberMessageCode.MEMBER_NOT_EXIST;

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

    private final MatchingRepository matchingRepository;

    private final PaymentRepository paymentRepository;

    private final EntityManager em;

    /**
     * 산책 허락하기 메서드
     */
    @Transactional
    public void saveWalk(CustomUserDetails customUserDetails, Long userId, Long matchingId) throws MemberNotExistException{
        Member master = memberJpaRepository.findById(customUserDetails.getMember().getId()).orElseThrow(() -> new RuntimeException("올바르지 않은 멤버 ID입니다."));
        Member walker = memberJpaRepository.findById(userId).orElseThrow(() -> new MemberNotExistException(MEMBER_NOT_EXIST));


        Match match = matchingRepository.findMatchById(matchingId).orElseThrow(() -> new MatchNotExistException(MessageCode.MATCH_NOT_EXIST));

        Notification notification = match.getNotificationId();
        walkRepository.save(Walk.of(master, walker, notification));
    }

    /**
     * 산책 시작하기 메서드
     */
    @Transactional
    public WalkRespDTO.StartWalk startWalk(Long matchingId) throws WalkNotExistException{
        Walk walk = matchingRepository.findWalkFromMatchById(matchingId).orElseThrow(() -> new WalkNotExistException(MessageCode.WALK_NOT_EXIST));
        walk.startWalk();

        return new WalkRespDTO.StartWalk(walk);
    }

    /**
     * 산책 종료하기 메서드
     */
    @Transactional
    public WalkRespDTO.EndWalk terminateWalk(CustomUserDetails customUserDetails, Long matchingId) throws WalkNotExistException {
        Notification notification = matchingRepository.findMatchById(matchingId)
                .orElseThrow(() -> new MatchNotExistException(MessageCode.MATCH_NOT_EXIST))
                .getNotificationId();

        Walk walk = matchingRepository.findWalkFromMatchById(matchingId).orElseThrow(() -> new WalkNotExistException(MessageCode.WALK_NOT_EXIST));

        walk.endWalk();

        Payment payment = paymentRepository.findById(walk.getId()).orElseThrow(() -> new RuntimeException("잘못된 결제 id입니다."));
        Member walker = walk.getWalker();

        PaymentService.depositFromPayment(payment, walker);

        return WalkRespDTO.EndWalk.of(customUserDetails.getMember(), walker, walk, notification);
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

