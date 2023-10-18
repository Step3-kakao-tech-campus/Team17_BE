package com.kakaoseventeen.dogwalking.member.controller;

import com.kakaoseventeen.dogwalking._core.utils.ApiResponse;
import com.kakaoseventeen.dogwalking._core.utils.ApiResponseGenerator;
import com.kakaoseventeen.dogwalking.member.dto.LoginRequestDTO;
import com.kakaoseventeen.dogwalking.member.dto.LoginResponseDTO;
import com.kakaoseventeen.dogwalking.member.dto.UpdateProfileReqDTO;
import com.kakaoseventeen.dogwalking.member.dto.UpdateProfileRespDTO;
import com.kakaoseventeen.dogwalking.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO respDTO = memberService.login(loginRequestDTO);
        return ApiResponseGenerator.success(respDTO, HttpStatus.OK);
    }

    @PostMapping("/profile/user/{userId}")
    public ApiResponse<ApiResponse.CustomBody<UpdateProfileRespDTO>> updateProfile(@RequestBody UpdateProfileReqDTO reqDTO, @PathVariable("userId") Long userId) {
        UpdateProfileRespDTO respDTO = memberService.updateProfile(reqDTO, userId);
        return ApiResponseGenerator.success(respDTO, HttpStatus.OK);

    }
}
