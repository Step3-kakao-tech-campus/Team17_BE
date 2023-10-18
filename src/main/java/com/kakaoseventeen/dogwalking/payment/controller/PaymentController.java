package com.kakaoseventeen.dogwalking.payment.controller;

import com.kakaoseventeen.dogwalking._core.security.CustomUserDetails;
import com.kakaoseventeen.dogwalking._core.utils.ApiResponse;
import com.kakaoseventeen.dogwalking._core.utils.ApiResponseGenerator;
import com.kakaoseventeen.dogwalking.payment.dto.PaymentResDTO;
import com.kakaoseventeen.dogwalking.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * 결제 정보 조회 메서드
     */
    @GetMapping("payment/{matchingId}")
    public ApiResponse<ApiResponse.CustomBody<PaymentResDTO>> acceptWalk(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("matchingId") Long matchingId) throws RuntimeException {
        PaymentResDTO resDTO = paymentService.getPaymentInfo(customUserDetails, matchingId);
        return ApiResponseGenerator.success(resDTO, HttpStatus.OK);
    }
}
