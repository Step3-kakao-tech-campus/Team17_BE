package com.kakaoseventeen.dogwalking.payment.service;

import com.kakaoseventeen.dogwalking._core.security.CustomUserDetails;
import com.kakaoseventeen.dogwalking._core.utils.MessageCode;
import com.kakaoseventeen.dogwalking._core.utils.exception.MatchNotExistException;
import com.kakaoseventeen.dogwalking._core.utils.exception.WalkNotExistException;
import com.kakaoseventeen.dogwalking.match.domain.Match;
import com.kakaoseventeen.dogwalking.match.repository.MatchingRepository;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.notification.repository.NotificationJpaRepository;
import com.kakaoseventeen.dogwalking.payment.domain.Payment;
import com.kakaoseventeen.dogwalking.payment.dto.PaymentResDTO;
import com.kakaoseventeen.dogwalking.payment.repository.PaymentRepository;
import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final MatchingRepository matchingRepository;

    @Transactional(readOnly = true)
    public PaymentResDTO getPaymentInfo(CustomUserDetails customUserDetails, Long matchingId){
        // notification 가져오기
        Notification notification = matchingRepository.findMatchById(matchingId)
                .orElseThrow(() -> new MatchNotExistException(MessageCode.MATCH_NOT_EXIST)).getNotificationId();

        // walk 가져오기
        Walk walk = matchingRepository.findWalkFromMatchById(matchingId).orElseThrow(() -> new WalkNotExistException(MessageCode.WALK_NOT_EXIST));

        // notification, walk, member 엔티티들을 dto.of()을 통해 생성 후 response
        return PaymentResDTO.of(customUserDetails.getMember(), walk, notification);
    }


    /**
     * 알바에게 멍코인을 입금하는 메서드
     */
    public static void depositFromPayment(Payment p){

    }

    /**
     * 견주에게서 돈을 인출하는 메서드
     */
    public static void withdrawFromMaster(Payment p){

    }

}
