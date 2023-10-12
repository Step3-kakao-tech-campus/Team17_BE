package com.kakaoseventeen.dogwalking.payment.repository;

import com.kakaoseventeen.dogwalking.payment.domain.Payment;
import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Payment(결제) 레포지토리
 *
 * @author 승건 이
 * @version 1.0
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    /**
     * paymentId를 통해서 Walk 엔티티의 Dog 엔티티를 가져오는 쿼리
     */
    @Query("select p.walk from Payment p join fetch p.walk.dog where p.id = :paymentId")
    Optional<Walk> findByWalkWithPaymentId(Long paymentId);

}
