package com.kakaoseventeen.dogwalking.application.controller;


import com.kakaoseventeen.dogwalking._core.utils.ApiResponse;
import com.kakaoseventeen.dogwalking._core.utils.ApiResponseGenerator;
import com.kakaoseventeen.dogwalking.application.dto.CreateAppReqDTO;
import com.kakaoseventeen.dogwalking.application.dto.GetAppResDTO;
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

    @GetMapping("/application")
    public ApiResponse<ApiResponse.CustomBody<GetAppResDTO>> getApp(@RequestParam Long id){

        GetAppResDTO response = applicationReadService.getApp(id);

        return ApiResponseGenerator.success(response, HttpStatus.OK);
    }

    @PostMapping("/application")
    public ResponseEntity<?> createApp(@RequestBody CreateAppReqDTO createAppReqDTO){
        applicationWriteService.createApp(createAppReqDTO);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }
}
