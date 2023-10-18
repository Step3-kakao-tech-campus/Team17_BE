package com.kakaoseventeen.dogwalking.notification.controller;

import com.kakaoseventeen.dogwalking._core.utils.CursorRequest;
import com.kakaoseventeen.dogwalking.notification.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HomeController {

    private final HomeService homeService;

    @GetMapping("/home")
    public ResponseEntity<?> getPosts(CursorRequest cursorRequest,
                                      @RequestParam Double latitude,
                                      @RequestParam Double longitude,
                                      @RequestParam List<String> size,
                                      @RequestParam List<String> breed) {


        homeService.home(cursorRequest,latitude,longitude, size, breed);

        return ResponseEntity.ok().build();
    }
}
