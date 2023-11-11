package com.kakaoseventeen.dogwalking.walk.service;

import com.kakaoseventeen.dogwalking._core.security.CustomUserDetails;
import com.kakaoseventeen.dogwalking._core.utils.MessageCode;
import com.kakaoseventeen.dogwalking._core.utils.exception.*;
import com.kakaoseventeen.dogwalking._core.utils.exception.member.MemberNotExistException;
import com.kakaoseventeen.dogwalking._core.utils.exception.payment.PaymentNotExistException;
import com.kakaoseventeen.dogwalking._core.utils.exception.walk.DuplicateNotificationWithWalkException;
import com.kakaoseventeen.dogwalking._core.utils.exception.walk.WalkNotExistException;
import com.kakaoseventeen.dogwalking.match.domain.Match;
import com.kakaoseventeen.dogwalking.match.repository.MatchRepository;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.payment.domain.Payment;
import com.kakaoseventeen.dogwalking.payment.repository.PaymentRepository;
import com.kakaoseventeen.dogwalking.payment.service.PaymentService;
import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import com.kakaoseventeen.dogwalking.walk.dto.WalkResDTO;
import com.kakaoseventeen.dogwalking.walk.repository.WalkRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.kakaoseventeen.dogwalking._core.utils.MemberMessageCode.MEMBER_NOT_EXIST;

/**
 * Walk(산책) 서비스
 *
 * @author 승건 이
 * @version 1.0
 */
@Log4j
@Service
@RequiredArgsConstructor
public class WalkService {

    private final WalkRepository walkRepository;

    private final MemberRepository memberRepository;

    private final MatchRepository matchRepository;

    private final PaymentRepository paymentRepository;

    /**
     * 산책 허락하기 메서드
     */
    @Transactional
    public void saveWalk(CustomUserDetails customUserDetails, Long userId, Long matchingId) throws MatchNotExistException, DuplicateNotificationWithWalkException, MemberNotExistException {
        Member master = memberRepository.findById(customUserDetails.getMember().getId()).orElseThrow(() -> new MemberNotExistException(MEMBER_NOT_EXIST));
        Member walker = memberRepository.findById(userId).orElseThrow(() -> new MemberNotExistException(MEMBER_NOT_EXIST));


        Match match = matchRepository.findMatchById(matchingId).orElseThrow(() -> new MatchNotExistException(MessageCode.MATCH_NOT_EXIST));

        Notification notification = match.getNotificationId();

        if (notification.getWalk() != null) {
            throw new DuplicateNotificationWithWalkException(MessageCode.DUPLICATE_NOTIFICATION);
        }

        walkRepository.save(Walk.of(master, walker, notification));
    }

    /**
     * 산책 시작하기 메서드
     */
    @Transactional
    public WalkResDTO.StartWalk startWalk(Long matchingId) throws WalkNotExistException {
        Walk walk = matchRepository.findWalkFromMatchById(matchingId).orElseThrow(() -> new WalkNotExistException(MessageCode.WALK_NOT_EXIST));
        walk.startWalk();

        return new WalkResDTO.StartWalk(walk);
    }

    /**
     * 산책 종료하기 메서드
     */
    @Transactional
    public WalkResDTO.EndWalk terminateWalk(CustomUserDetails customUserDetails, Long matchingId) throws MatchNotExistException, WalkNotExistException, PaymentNotExistException {
        Notification notification = matchRepository.findMatchById(matchingId)
                .orElseThrow(() -> new MatchNotExistException(MessageCode.MATCH_NOT_EXIST))
                .getNotificationId();

        Walk walk = matchRepository.findWalkFromMatchById(matchingId).orElseThrow(() -> new WalkNotExistException(MessageCode.WALK_NOT_EXIST));

        walk.endWalk();

        Payment payment = paymentRepository.findById(walk.getId()).orElseThrow(() -> new PaymentNotExistException(MessageCode.PAYMENT_NOT_EXIST));
        Member walker = walk.getWalker();

        PaymentService.depositFromPayment(payment, walker);

        return WalkResDTO.EndWalk.of(customUserDetails.getMember(), walker, walk, notification);
    }

    /**
     * 종료되고, 리뷰가 작성되지 않은 산책 반환 메서드
     */
    @Transactional(readOnly = true)
    public WalkResDTO.FindNotEndWalksByUserId findAllWalkStatusByUserId(CustomUserDetails customUserDetails) throws MemberNotExistException {
        Member member = memberRepository.findById(customUserDetails.getMember().getId()).orElseThrow(() -> new MemberNotExistException(MEMBER_NOT_EXIST));

        List<Walk> walks = walkRepository.findWalkWhatNotEnd(member.getId());

        return new WalkResDTO.FindNotEndWalksByUserId(walks, member.getId());
    }
}

