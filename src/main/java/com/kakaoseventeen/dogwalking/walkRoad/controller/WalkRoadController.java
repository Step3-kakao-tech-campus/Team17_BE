package com.kakaoseventeen.dogwalking.walkRoad.controller;

import com.kakaoseventeen.dogwalking._core.utils.ApiResponse;
import com.kakaoseventeen.dogwalking._core.utils.ApiResponseGenerator;

import com.kakaoseventeen.dogwalking.walkRoad.dto.WalkRoadReqDTO;
import com.kakaoseventeen.dogwalking.walkRoad.dto.WalkRoadResDTO;
import com.kakaoseventeen.dogwalking.walkRoad.service.WalkRoadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class WalkRoadController {

    private final WalkRoadService walkRoadService;

    /**
     * 산책 경로 저장 메서드
     */
    @PostMapping("/walkRoad/{matchId}")
    public ApiResponse<ApiResponse.CustomBody<WalkRoadResDTO.SaveWalkResp>> saveWalkRoad(@RequestBody WalkRoadReqDTO walkRoadReqDTO, @PathVariable("matchId") Long matchId) throws RuntimeException {
        WalkRoadResDTO.SaveWalkResp respDTO = walkRoadService.saveWalkRoad(walkRoadReqDTO, matchId);
        return ApiResponseGenerator.success(respDTO, HttpStatus.OK);
    }


    /**
     * 산책 경로 조회 메서드
     */
    @GetMapping("/walkRoad/{matchId}")
    public ApiResponse<ApiResponse.CustomBody<WalkRoadResDTO.FindByWalkId>> findAllByWalkId(@PathVariable("matchId") Long matchId) throws RuntimeException {
        WalkRoadResDTO.FindByWalkId respDTO = walkRoadService.findAllByWalkId(matchId);
        return ApiResponseGenerator.success(respDTO, HttpStatus.OK);
    }
}
