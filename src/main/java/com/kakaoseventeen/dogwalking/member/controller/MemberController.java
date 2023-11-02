package com.kakaoseventeen.dogwalking.member.controller;

import com.kakaoseventeen.dogwalking._core.security.CustomUserDetails;
import com.kakaoseventeen.dogwalking._core.utils.ApiResponse;
import com.kakaoseventeen.dogwalking._core.utils.ApiResponseGenerator;
import com.kakaoseventeen.dogwalking._core.utils.exception.MemberNotExistException;
import com.kakaoseventeen.dogwalking.member.dto.*;
import com.kakaoseventeen.dogwalking.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/member/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginReqDTO loginReqDTO, Errors error) {
        LoginRespDTO respDTO = memberService.login(loginReqDTO);
        return ApiResponseGenerator.success(respDTO, HttpStatus.OK);
    }

    @PostMapping("/profile/user/{userId}")
    public ApiResponse<ApiResponse.CustomBody<UpdateProfileRespDTO>> updateProfile(@RequestBody UpdateProfileReqDTO reqDTO, @PathVariable("userId") Long userId) {
        UpdateProfileRespDTO respDTO = memberService.updateProfile(reqDTO, userId);
        return ApiResponseGenerator.success(respDTO, HttpStatus.OK);
    }

    @PostMapping("/profile/isOwner/{userId}")
    public ApiResponse<ApiResponse.CustomBody<IsOwnerRespDTO>> isProfileOwner(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("userId") Long userId) {
        IsOwnerRespDTO respDTO = memberService.isProfileOwner(customUserDetails, userId);
        return ApiResponseGenerator.success(respDTO, HttpStatus.OK);
    }

    /**
     * 프로필 조회 메서드
     */
    @GetMapping("/profile/{userId}")
    public ApiResponse<ApiResponse.CustomBody<MemberProfileRespDTO>> getProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable(value = "userId", required = false) Long userId) throws MemberNotExistException {
        MemberProfileRespDTO respDTO = memberService.getProfile(customUserDetails, userId);
        return ApiResponseGenerator.success(respDTO, HttpStatus.OK);
    }

    @PostMapping("/member/signup")
    public ResponseEntity<?> signup(@RequestBody SignupReqDTO signupReqDTO){

        memberService.signup(signupReqDTO);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

}
