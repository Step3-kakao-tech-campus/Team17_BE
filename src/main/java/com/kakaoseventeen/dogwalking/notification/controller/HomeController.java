package com.kakaoseventeen.dogwalking.notification.controller;

import com.kakaoseventeen.dogwalking._core.security.CustomUserDetails;
import com.kakaoseventeen.dogwalking._core.utils.ApiResponseGenerator;
import com.kakaoseventeen.dogwalking._core.utils.CursorRequest;
import com.kakaoseventeen.dogwalking.notification.dto.response.HomeResponse;
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

    @GetMapping("/home")
    public ResponseEntity<?> getPosts(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                      CursorRequest cursorRequest,
                                      @RequestParam Double latitude,
                                      @RequestParam Double longitude,
                                      @RequestParam(required = false) List<String> big,
                                      @RequestParam(required = false) List<String> breed,
                                      @RequestParam(required = false) String search
                                      ) {

        if(big == null) big = List.of();
        if(breed == null) breed = List.of();
        if(search == null) search = "";

        HomeResponse respDTO = homeService.home(cursorRequest,latitude,longitude, big, breed, search, customUserDetails);

        return ApiResponseGenerator.success(respDTO, HttpStatus.OK);
    }
}