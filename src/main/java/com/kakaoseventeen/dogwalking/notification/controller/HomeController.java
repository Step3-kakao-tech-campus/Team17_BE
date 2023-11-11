package com.kakaoseventeen.dogwalking.notification.controller;

import com.kakaoseventeen.dogwalking._core.security.CustomUserDetails;
import com.kakaoseventeen.dogwalking._core.utils.ApiResponseGenerator;
import com.kakaoseventeen.dogwalking._core.utils.CursorRequest;
import com.kakaoseventeen.dogwalking.notification.dto.response.HomeResDTO;
import com.kakaoseventeen.dogwalking.notification.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HomeController {

    private final HomeService homeService;

    /**
     * 메인페이지에서 공고글을 불러오는 메서드
     * @param customUserDetails 유저 정보
     * @param cursorRequest 커서 정보
     * @param latitude 위도
     * @param longitude 경도
     * @param big 강아지 사이즈
     * @param breed 견종
     * @param search 검색어
     * @return
     */
    @GetMapping("/home")
    public ResponseEntity<?> getPosts(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                      CursorRequest cursorRequest,
                                      @RequestParam(value = "latitude") Double latitude,
                                      @RequestParam(value = "longitude") Double longitude,
                                      @RequestParam(required = false, value = "big") List<String> big,
                                      @RequestParam(required = false, value = "breed") List<String> breed,
                                      @RequestParam(required = false, value = "word") String search
                                      ) {
        
        if(big == null) big = List.of();
        if(breed == null) breed = List.of();
        if(search == null) search = "";

        HomeResDTO respDTO = homeService.home(cursorRequest,latitude,longitude, big, breed, search, customUserDetails);

        return ApiResponseGenerator.success(respDTO, HttpStatus.OK);
    }
}