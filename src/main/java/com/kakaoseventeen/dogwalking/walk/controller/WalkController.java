package com.kakaoseventeen.dogwalking.walk.controller;

import com.kakaoseventeen.dogwalking.walk.service.WalkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class WalkController {

    private final WalkService walkService;


}
