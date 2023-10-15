package com.kakaoseventeen.dogwalking.notification.service;


import com.kakaoseventeen.dogwalking._core.utils.CursorRequest;
import com.kakaoseventeen.dogwalking.notification.repository.NotificationJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class HomeService {
    private final NotificationJpaRepository notificationJpaRepository;

    public void home(CursorRequest cursorRequest, Double lat, Double lng, List<String> size, List<String> breed) {


    }
}