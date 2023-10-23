package com.kakaoseventeen.dogwalking.walk.controller;

import com.kakaoseventeen.dogwalking._core.security.CustomUserDetails;
import com.kakaoseventeen.dogwalking._core.utils.ApiResponse;
import com.kakaoseventeen.dogwalking._core.utils.ApiResponseGenerator;

import com.kakaoseventeen.dogwalking._core.utils.exception.WalkNotExistException;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.walk.dto.WalkRespDTO;
import com.kakaoseventeen.dogwalking.walk.service.WalkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class WalkController {

    private final WalkService walkService;

    /**
     * 산책 허락하기 메서드
     */
    @PostMapping("walk/{walkerId}/{matchingId}")
    public ApiResponse<ApiResponse.CustomBody<Void>> acceptWalk(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("walkerId") Long userId, @PathVariable("matchingId") Long matchingId) throws RuntimeException {
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
    public ApiResponse<ApiResponse.CustomBody<WalkRespDTO.EndWalk>> endWalk(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("matchingId") Long matchingId) throws WalkNotExistException {
        WalkRespDTO.EndWalk respDTO = walkService.terminateWalk(customUserDetails, matchingId);
        return ApiResponseGenerator.success(respDTO, HttpStatus.OK);
    }
}
