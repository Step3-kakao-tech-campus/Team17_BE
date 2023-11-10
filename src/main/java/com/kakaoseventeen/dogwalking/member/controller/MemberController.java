package com.kakaoseventeen.dogwalking.member.controller;

import com.kakaoseventeen.dogwalking._core.security.CustomUserDetails;
import com.kakaoseventeen.dogwalking._core.utils.ApiResponse;
import com.kakaoseventeen.dogwalking._core.utils.ApiResponseGenerator;
import com.kakaoseventeen.dogwalking._core.utils.exception.MemberNotExistException;
import com.kakaoseventeen.dogwalking.member.dto.*;
import com.kakaoseventeen.dogwalking.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {
    private final MemberService memberService;

    /**
     * 로그인 메서드
     */
    @PostMapping("/member/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginReqDTO loginReqDTO, Errors error) {
        LoginRespDTO respDTO = memberService.login(loginReqDTO);
        return ApiResponseGenerator.success(respDTO, HttpStatus.OK);
    }

    /**
     * 프로필 수정 메서드
     */
    @PostMapping(value = "/profile/user", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse<ApiResponse.CustomBody<UpdateProfileRespDTO>> updateProfile(@ModelAttribute UpdateProfileReqDTO reqDTO,
                                                                                   @AuthenticationPrincipal CustomUserDetails customUserDetails) throws IOException, MemberNotExistException {
        UpdateProfileRespDTO respDTO = memberService.updateProfile(customUserDetails, reqDTO.getProfileImage(), reqDTO.getProfileContent());
        return ApiResponseGenerator.success(respDTO, HttpStatus.OK);
    }

    /**
     * 유저 프로필 조회간 Member 엔티티를 이용해 각 Repository에 쿼리를 보내서 값을 가져오는 메서드
     */
    @PostMapping("/profile/isOwner/{userId}")
    public ApiResponse<ApiResponse.CustomBody<IsOwnerRespDTO>> isProfileOwner(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("userId") Long userId) {
        IsOwnerRespDTO respDTO = memberService.isProfileOwner(customUserDetails, userId);
        return ApiResponseGenerator.success(respDTO, HttpStatus.OK);
    }

    /**
     * 프로필 조회 메서드
     */
    @GetMapping(value = {"/profile/{userId}", "/profile"})
    public ApiResponse<ApiResponse.CustomBody<MemberProfileRespDTO>> getProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable(required = false) Long userId) throws MemberNotExistException {
        MemberProfileRespDTO respDTO = memberService.getProfile(customUserDetails, userId);
        return ApiResponseGenerator.success(respDTO, HttpStatus.OK);
    }

    /**
     * 회원가입 메서드
     */
    @PostMapping("/member/signup")
    public ResponseEntity<?> signup(@RequestBody SignupReqDTO signupReqDTO){

        memberService.signup(signupReqDTO);
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

}
