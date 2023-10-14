package com.kakaoseventeen.dogwalking.walk.controller;

import com.kakaoseventeen.dogwalking._core.utils.ApiResponse;
import com.kakaoseventeen.dogwalking._core.utils.ApiResponseGenerator;
import com.kakaoseventeen.dogwalking.walk.dto.WalkRespDTO;
import com.kakaoseventeen.dogwalking.walk.service.WalkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
     * @param masterId -> 추후 JWT를 통한 인증 객체인 Authentication 객체로 변경 되어야 함.
     */
    @PostMapping("walk/{masterId}/{walkerId}/{chatRoomId}")
    public ApiResponse<ApiResponse.CustomBody<Void>> acceptWalk(@PathVariable("masterId") Long masterId, @PathVariable("walkerId") Long walkerId, @PathVariable("chatRoomId") Long chatRoomId) throws RuntimeException {
        walkService.saveWalk(masterId, walkerId, chatRoomId);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    /**
     * 산책 시작하기 메서드
     */
    @PostMapping("walk/start/{chatRoomId}")
    public ApiResponse<ApiResponse.CustomBody<WalkRespDTO.StartWalk>> startWalk(@PathVariable("chatRoomId") Long chatRoomId) throws RuntimeException {
        WalkRespDTO.StartWalk respDTO = walkService.startWalk(chatRoomId);
        return ApiResponseGenerator.success(respDTO, HttpStatus.OK);
    }

    /**
     * 산책 종료하기 메서드
     */
    @PostMapping("walk/end/{chatRoomId}")
    public ApiResponse<ApiResponse.CustomBody<WalkRespDTO.EndWalk>> endWalk(@PathVariable("chatRoomId") Long chatRoomId) throws RuntimeException {
        WalkRespDTO.EndWalk respDTO = walkService.terminateWalk(chatRoomId);
        return ApiResponseGenerator.success(respDTO, HttpStatus.OK);
    }
}
