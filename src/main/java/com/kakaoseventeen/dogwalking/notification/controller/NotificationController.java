package com.kakaoseventeen.dogwalking.notification.controller;

import com.kakaoseventeen.dogwalking._core.security.CustomUserDetails;
import com.kakaoseventeen.dogwalking._core.utils.ApiResponseGenerator;
import com.kakaoseventeen.dogwalking.notification.dto.request.WriteNotificationReqDTO;
import com.kakaoseventeen.dogwalking.notification.dto.response.LoadDogResDTO;
import com.kakaoseventeen.dogwalking.notification.dto.response.LoadNotificationResDTO;
import com.kakaoseventeen.dogwalking.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NotificationController {
    private final NotificationService notificationService;

    /**
     * 공고글 강아지 불러오기 메서드
     */
    @GetMapping("/notification")
    public ResponseEntity<?> loadDog(@AuthenticationPrincipal CustomUserDetails userDetails){
        LoadDogResDTO respDTO = notificationService.loadDog(userDetails.getMember());
        return ApiResponseGenerator.success(respDTO, HttpStatus.OK);
    }

    /**
     * 공고글 상세보기 메서드
     */
    @GetMapping("/notification/{notificationId}")
    public ResponseEntity<?> loadNotification(@PathVariable Long notificationId, @AuthenticationPrincipal CustomUserDetails userDetails){
        LoadNotificationResDTO respDTO = notificationService.loadNotification(notificationId, userDetails.getMember());
        return ApiResponseGenerator.success(respDTO, HttpStatus.OK);

    }

    /**
     * 공고글 작성하기 메서드
     */
    @PostMapping("/notification")
    public ResponseEntity<?> writeNotification(@AuthenticationPrincipal CustomUserDetails userDetails, @Valid @RequestBody WriteNotificationReqDTO requestDTO, Errors errors){
        notificationService.writeNotification(requestDTO, userDetails.getMember());
        return ApiResponseGenerator.success(HttpStatus.OK);
    }

}
