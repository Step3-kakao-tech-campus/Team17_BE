package com.kakaoseventeen.dogwalking.application.controller;


import com.kakaoseventeen.dogwalking._core.utils.ApiResponse;
import com.kakaoseventeen.dogwalking._core.utils.ApiResponseGenerator;
import com.kakaoseventeen.dogwalking.application.dto.request.CreateAppReqDTO;
import com.kakaoseventeen.dogwalking.application.dto.response.GetAppMemberResDTO;
import com.kakaoseventeen.dogwalking.application.dto.response.GetAppResDTO;
import com.kakaoseventeen.dogwalking.application.service.ApplicationReadService;
import com.kakaoseventeen.dogwalking.application.service.ApplicationWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApplicationController {

    private final ApplicationWriteService applicationWriteService;
    private final ApplicationReadService applicationReadService;

    /**
     * 지원서 조회 메서드
     */
    @GetMapping("/application/{applicationId}")
    public ApiResponse<ApiResponse.CustomBody<GetAppResDTO>> getApp(@PathVariable Long applicationId){

        GetAppResDTO response = applicationReadService.getApp(applicationId);

        return ApiResponseGenerator.success(response, HttpStatus.OK);
    }

    /**
     * 지원서 작성 메서드
     */
    @PostMapping("/application")
    public ResponseEntity<?> createApp(@RequestBody CreateAppReqDTO createAppReqDTO){
        applicationWriteService.createApp(createAppReqDTO);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

    /**
     * 지원서 불러오기 메서드
     */
    @GetMapping("/application")
    public ApiResponse<ApiResponse.CustomBody<GetAppMemberResDTO>> getAppMember(){

         GetAppMemberResDTO response = applicationReadService.getAppMember();

        return ApiResponseGenerator.success(response, HttpStatus.OK);

    }
}
