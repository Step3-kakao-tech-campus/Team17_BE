package com.kakaoseventeen.dogwalking.notification.controller;

import com.kakaoseventeen.dogwalking._core.security.CustomUserDetails;
import com.kakaoseventeen.dogwalking._core.utils.ApiUtils;
import com.kakaoseventeen.dogwalking.notification.dto.response.LoadDogResponseDTO;
import com.kakaoseventeen.dogwalking.notification.dto.response.LoadNotificationResponseDTO;
import com.kakaoseventeen.dogwalking.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class NotificationRestController {
    private final NotificationService notificationService;

    @GetMapping("/notification")
    public ResponseEntity<?> loadDog(@AuthenticationPrincipal CustomUserDetails userDetails){
        LoadDogResponseDTO responseDTO = notificationService.loadDog(userDetails.getMember());
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/notification/{id}")
    public ResponseEntity<?> loadNotification(@PathVariable int id, @AuthenticationPrincipal CustomUserDetails userDetails){
        LoadNotificationResponseDTO responseDTO = notificationService.loadNotification(id, userDetails.getMember());
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }

}
