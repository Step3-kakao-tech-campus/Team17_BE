package com.kakaoseventeen.dogwalking.notification.controller;

import com.kakaoseventeen.dogwalking._core.security.CustomUserDetails;
import com.kakaoseventeen.dogwalking._core.utils.ApiResponseGenerator;
import com.kakaoseventeen.dogwalking._core.utils.CursorRequest;
import com.kakaoseventeen.dogwalking.notification.dto.response.HomeRespDTO;
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
     * 메인페이지에서 위도, 경도, 견종, 크기, 검색어를 통해 포스트를 가져오는 메서드
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

        HomeRespDTO respDTO = homeService.home(cursorRequest,latitude,longitude, big, breed, search, customUserDetails);

        return ApiResponseGenerator.success(respDTO, HttpStatus.OK);
    }
}