package com.kakaoseventeen.dogwalking._core.walkRoad.controller;

import com.kakaoseventeen.dogwalking._core.utils.ApiResponse;
import com.kakaoseventeen.dogwalking._core.utils.ApiResponseGenerator;

import com.kakaoseventeen.dogwalking._core.walkRoad.dto.WalkRoadReqDTO;
import com.kakaoseventeen.dogwalking._core.walkRoad.dto.WalkRoadRespDTO;
import com.kakaoseventeen.dogwalking._core.walkRoad.service.WalkRoadService;
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
    @PostMapping("/walkRoad/{walkId}")
    public ApiResponse<ApiResponse.CustomBody<WalkRoadRespDTO.SaveWalkResp>> saveWalkRoad(@RequestBody WalkRoadReqDTO walkRoadReqDTO, @PathVariable("walkId") Long walkId) throws RuntimeException {
        WalkRoadRespDTO.SaveWalkResp respDTO = walkRoadService.saveWalkRoad(walkRoadReqDTO, walkId);
        return ApiResponseGenerator.success(respDTO, HttpStatus.OK);
    }


    /**
     * 산책 경로 조회 메서드
     */
    @GetMapping("/walkRoad/{walkId}")
    public ApiResponse<ApiResponse.CustomBody<WalkRoadRespDTO.FindByWalkId>> findAllByWalkId(@PathVariable("walkId") Long walkId) throws RuntimeException {
        WalkRoadRespDTO.FindByWalkId respDTO = walkRoadService.findAllByWalkId(walkId);
        return ApiResponseGenerator.success(respDTO, HttpStatus.OK);
    }
}
