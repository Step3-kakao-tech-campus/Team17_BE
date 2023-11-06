package com.kakaoseventeen.dogwalking.match.controller;

import com.kakaoseventeen.dogwalking._core.security.CustomUserDetails;
import com.kakaoseventeen.dogwalking._core.utils.ApiResponseGenerator;
import com.kakaoseventeen.dogwalking.match.dto.MatchRespDTO;
import com.kakaoseventeen.dogwalking.match.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MatchController {

    private final MatchService matchService;

    @GetMapping("/notification/{notificationId}/match")
    public ResponseEntity<?> findMatch(@PathVariable("notificationId") Long notificationId, @AuthenticationPrincipal CustomUserDetails customUserDetails) throws RuntimeException {
        MatchRespDTO respDTO = matchService.match(notificationId, customUserDetails.getMember());
        return ApiResponseGenerator.success(respDTO, HttpStatus.OK);
    }
}
