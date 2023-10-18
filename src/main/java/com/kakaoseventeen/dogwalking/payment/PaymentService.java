package com.kakaoseventeen.dogwalking.payment;

import com.kakaoseventeen.dogwalking.payment.domain.Payment;
import com.kakaoseventeen.dogwalking.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

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
