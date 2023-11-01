package com.kakaoseventeen.dogwalking.review.controller;

import com.kakaoseventeen.dogwalking._core.utils.ApiResponseGenerator;
import com.kakaoseventeen.dogwalking.review.dto.WriteReviewReqDTO;
import com.kakaoseventeen.dogwalking.review.service.ReviewWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewWriteService reviewWriteService;

    @PostMapping("/review")
    public ResponseEntity<?> writeReview(@RequestBody WriteReviewReqDTO writeReviewReqDTO){

        reviewWriteService.writeReview(writeReviewReqDTO);

        return ApiResponseGenerator.success(HttpStatus.OK);

    }
}
