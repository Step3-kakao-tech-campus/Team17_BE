package com.kakaoseventeen.dogwalking.application.service;

import com.kakaoseventeen.dogwalking._core.utils.ApplicationMessageCode;
import com.kakaoseventeen.dogwalking._core.utils.exception.application.ApplicationMemberNotFoundException;
import com.kakaoseventeen.dogwalking._core.utils.exception.application.NotificationNotFoundException;
import com.kakaoseventeen.dogwalking.application.domain.Application;
import com.kakaoseventeen.dogwalking.application.dto.CreateAppReqDTO;
import com.kakaoseventeen.dogwalking.application.repository.ApplicationRepository;
import com.kakaoseventeen.dogwalking.match.domain.Match;
import com.kakaoseventeen.dogwalking.match.repository.MatchingRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.notification.repository.NotificationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationWriteService {

    private final ApplicationRepository applicationRepository;
    private final MatchingRepository matchingRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final NotificationJpaRepository notificationJpaRepository;

    @Transactional
    public void createApp(CreateAppReqDTO createAppReqDTO){

        Member member = memberJpaRepository.findByEmail(getEmail())
                .orElseThrow(() -> new ApplicationMemberNotFoundException(ApplicationMessageCode.MEMBER_NOT_FOUND));

        Application application = getApplication(createAppReqDTO, member);


        applicationRepository.save(application);

        Notification notification = notificationJpaRepository.findById(createAppReqDTO.notificationId())
                .orElseThrow(() -> new NotificationNotFoundException(ApplicationMessageCode.NOTIFICATION_NOT_FOUND));

        Match match = getMatch(application, notification);

        matchingRepository.save(match);


    }

    private String getEmail(){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String email = loggedInUser.getName();
        return email;
    }

    private static Match getMatch(Application application, Notification notification) {
        Match match = Match.builder()
                .applicationId(application)
                .notificationId(notification)
                .isSuccess(false)
                .build();
        return match;
    }

    private static Application getApplication(CreateAppReqDTO createAppReqDTO, Member member) {

        return Application.builder()
                .certification(createAppReqDTO.certification())
                .title(createAppReqDTO.title())
                .aboutMe(createAppReqDTO.aboutMe())
                .experience(createAppReqDTO.experience())
                .appMemberId(member)
                .build();
    }

}
