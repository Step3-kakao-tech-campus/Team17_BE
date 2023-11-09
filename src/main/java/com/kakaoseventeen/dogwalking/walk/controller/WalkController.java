package com.kakaoseventeen.dogwalking.walk.controller;

import com.kakaoseventeen.dogwalking._core.security.CustomUserDetails;
import com.kakaoseventeen.dogwalking._core.utils.ApiResponse;
import com.kakaoseventeen.dogwalking._core.utils.ApiResponseGenerator;

import com.kakaoseventeen.dogwalking._core.utils.exception.*;
import com.kakaoseventeen.dogwalking._core.utils.exception.member.MemberNotExistException;
import com.kakaoseventeen.dogwalking._core.utils.exception.payment.PaymentNotExistException;
import com.kakaoseventeen.dogwalking._core.utils.exception.walk.DuplicateNotificationWithWalkException;
import com.kakaoseventeen.dogwalking._core.utils.exception.walk.WalkNotExistException;
import com.kakaoseventeen.dogwalking.walk.dto.WalkRespDTO;
import com.kakaoseventeen.dogwalking.walk.service.WalkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class WalkController {

    private final WalkService walkService;

    /**
     * 산책 허락하기 메서드
     */
    @PostMapping("walk/{walkerId}/{matchingId}")
    public ApiResponse<ApiResponse.CustomBody<Void>> acceptWalk(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("walkerId") Long userId, @PathVariable("matchingId") Long matchingId)
            throws MatchNotExistException, DuplicateNotificationWithWalkException, MemberNotExistException {
        walkService.saveWalk(customUserDetails, userId, matchingId);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    /**
     * 산책 시작하기 메서드
     */
    @PostMapping("walk/start/{matchingId}")
    public ApiResponse<ApiResponse.CustomBody<WalkRespDTO.StartWalk>> startWalk(@PathVariable("matchingId") Long matchingId) throws WalkNotExistException {
        WalkRespDTO.StartWalk respDTO = walkService.startWalk(matchingId);
        return ApiResponseGenerator.success(respDTO, HttpStatus.OK);
    }

    /**
     * 산책 종료하기 메서드
     */
    @PostMapping("walk/end/{matchingId}")
    public ApiResponse<ApiResponse.CustomBody<WalkRespDTO.EndWalk>> endWalk(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("matchingId") Long matchingId) throws MatchNotExistException, WalkNotExistException, PaymentNotExistException {
        WalkRespDTO.EndWalk respDTO = walkService.terminateWalk(customUserDetails, matchingId);
        return ApiResponseGenerator.success(respDTO, HttpStatus.OK);
    }

    /**
     * 리뷰 작성되지 않은 산책 조회 메서드
     */
    @GetMapping("walk/notReviewed")
    public ApiResponse<ApiResponse.CustomBody<WalkRespDTO.FindNotEndWalksByUserId>> endWalk(@AuthenticationPrincipal CustomUserDetails customUserDetails) throws MemberNotExistException, WalkNotExistException{
        WalkRespDTO.FindNotEndWalksByUserId respDTO = walkService.findAllWalkStatusByUserId(customUserDetails);
        return ApiResponseGenerator.success(respDTO, HttpStatus.OK);
    }
}
