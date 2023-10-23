package com.kakaoseventeen.dogwalking.payment.service;

import com.kakaoseventeen.dogwalking._core.security.CustomUserDetails;
import com.kakaoseventeen.dogwalking._core.utils.MessageCode;
import com.kakaoseventeen.dogwalking._core.utils.exception.MatchNotExistException;
import com.kakaoseventeen.dogwalking._core.utils.exception.WalkNotExistException;
import com.kakaoseventeen.dogwalking.match.repository.MatchingRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.notification.repository.NotificationJpaRepository;
import com.kakaoseventeen.dogwalking.payment.domain.Payment;
import com.kakaoseventeen.dogwalking.payment.dto.PaymentResDTO;
import com.kakaoseventeen.dogwalking.payment.repository.PaymentRepository;
import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import com.kakaoseventeen.dogwalking.walk.repository.WalkRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PaymentService {

    private final EntityManager em;

    private final PaymentRepository paymentRepository;

    private final MatchingRepository matchingRepository;

    private final WalkRepository walkRepository;

    private final NotificationJpaRepository notificationJpaRepository;

    @Transactional(readOnly = true)
    public PaymentResDTO getPaymentInfo(CustomUserDetails customUserDetails, Long matchingId) throws WalkNotExistException{
        // notification 가져오기
        Notification notification = matchingRepository.findMatchById(matchingId)
                .orElseThrow(() -> new MatchNotExistException(MessageCode.MATCH_NOT_EXIST)).getNotificationId();

        // walk 가져오기
        Walk walk = matchingRepository.findWalkFromMatchById(matchingId).orElseThrow(() -> new WalkNotExistException(MessageCode.WALK_NOT_EXIST));

        // notification, walk, member 엔티티들을 dto.of()을 통해 생성 후 response
        return PaymentResDTO.of(customUserDetails.getMember(), walk, notification);
    }

    @Transactional
    public void savePayment(CustomUserDetails customUserDetails, Long notificationId, Long walkId){
        // notification 가져오기
        Notification notification = notificationJpaRepository.findById(notificationId).orElseThrow(() -> new RuntimeException("잘못된 공고 Id입니다."));

        // walk 가져오기
        Walk walk = walkRepository.findById(walkId).orElseThrow(() -> new WalkNotExistException(MessageCode.WALK_NOT_EXIST));

        // notification, walk, Payment.of()을 통해 생성 후 DB 저장
        Payment payment = paymentRepository.save(Payment.of(walk, notification.getCoin()));

        // 결제가 성공 되었으니 결제 활성화, walk.activateWalk() 호출
        walk.activateWalk();

        // 결제가 완료 되었으니, 견주의 코인에서 Payment tmpCoin 만큼 제해야한다.
        Member master = customUserDetails.getMember();

        withdrawFromMaster(payment, master);

        // 비영속 상태인 master를 직접 더티체킹
        em.merge(master);
    }

    /**
     * 알바에게 멍코인을 입금하는 메서드
     */
    public static void depositFromPayment(Payment p, Member m){
        m.depositCoin(p.getTmpMoney());

    }

    /**
     * 견주에게서 돈을 인출하는 메서드주
     */
    public static void withdrawFromMaster(Payment p, Member m){
        m.withdrawCoin(p.getTmpMoney());
    }
}
