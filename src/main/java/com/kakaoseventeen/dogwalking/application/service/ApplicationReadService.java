package com.kakaoseventeen.dogwalking.application.service;

import com.kakaoseventeen.dogwalking.application.domain.Application;
import com.kakaoseventeen.dogwalking.application.dto.GetAppResDTO;
import com.kakaoseventeen.dogwalking.application.repository.ApplicationRepository;
import com.kakaoseventeen.dogwalking.match.domain.Match;
import com.kakaoseventeen.dogwalking.match.repository.MatchingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationReadService {

    private final ApplicationRepository applicationRepository;
    private final MatchingRepository matchingRepository;


    public GetAppResDTO getApp(Long id) {

        // TODO - Custom 예외처리
        Application application = applicationRepository.findById(id).orElseThrow(() -> new RuntimeException("존재하지 않는 지원서 입니다."));
        Match match = matchingRepository.findMatchByApplicationId(id).orElseThrow(() -> new RuntimeException("잘못된 요청 입니다."));

        return GetAppResDTO.builder()
                .aboutMe(application.getAboutMe())
                .title(application.getTitle())
                .experience(application.getExperience())
                .notificationId(match.getNotificationId().getId())
                .memberImage(application.getAppMemberId().getProfileImage())
                .memberId(application.getAppMemberId().getId())
                .build();
    }

}
