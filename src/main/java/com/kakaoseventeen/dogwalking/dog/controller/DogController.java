package com.kakaoseventeen.dogwalking.dog.controller;

import com.kakaoseventeen.dogwalking._core.security.CustomUserDetails;
import com.kakaoseventeen.dogwalking._core.utils.ApiResponse;
import com.kakaoseventeen.dogwalking._core.utils.ApiResponseGenerator;
import com.kakaoseventeen.dogwalking._core.utils.exception.DogNotExistException;
import com.kakaoseventeen.dogwalking.dog.dto.DogReqDTO;
import com.kakaoseventeen.dogwalking.dog.dto.DogRespDTO;
import com.kakaoseventeen.dogwalking.dog.service.DogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DogController {

    private final DogService dogService;

    /**
     * 강아지 프로필 등록 메서드
     */
    @PostMapping("/profile/dog")
    public ApiResponse<ApiResponse.CustomBody<DogRespDTO.save>> saveDog(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody DogReqDTO dogReqDTO) throws RuntimeException {
        DogRespDTO.save respDTO = dogService.saveDog(dogReqDTO, customUserDetails);
        return ApiResponseGenerator.success(respDTO, HttpStatus.OK);
    }


    /**
     * 강아지 프로필 조회 메서드
     */
    @GetMapping("/profile/dog/{dogId}")
    public ApiResponse<ApiResponse.CustomBody<DogRespDTO.findById>> findByDogId(@PathVariable("dogId") long dogId) throws DogNotExistException {
        DogRespDTO.findById respDTO = dogService.findByDogId(dogId);
        return ApiResponseGenerator.success(respDTO, HttpStatus.OK);
    }
}
