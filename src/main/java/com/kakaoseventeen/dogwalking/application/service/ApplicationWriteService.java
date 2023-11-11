package com.kakaoseventeen.dogwalking.application.service;

import com.kakaoseventeen.dogwalking._core.utils.ApplicationMessageCode;
import com.kakaoseventeen.dogwalking._core.utils.exception.application.ApplicationMemberNotFoundException;
import com.kakaoseventeen.dogwalking._core.utils.exception.application.NotificationNotFoundException;
import com.kakaoseventeen.dogwalking.application.domain.Application;
import com.kakaoseventeen.dogwalking.application.dto.request.CreateAppReqDTO;
import com.kakaoseventeen.dogwalking.application.repository.ApplicationRepository;
import com.kakaoseventeen.dogwalking.match.domain.Match;
import com.kakaoseventeen.dogwalking.match.repository.MatchRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberRepository;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ApplicationWriteService (지원서 작성 서비스)
 *
 * @author 박영규
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class ApplicationWriteService {

    private final ApplicationRepository applicationRepository;
    private final MatchRepository matchRepository;
    private final MemberRepository memberRepository;
    private final NotificationRepository notificationRepository;

    /**
     * 지원서 작성 메서드
     */
    @Transactional
    public void createApp(CreateAppReqDTO createAppReqDTO){

        Member member = memberRepository.findByEmail(getEmail())
                .orElseThrow(() -> new ApplicationMemberNotFoundException(ApplicationMessageCode.MEMBER_NOT_FOUND));

        Application application = getApplication(createAppReqDTO, member);


        applicationRepository.save(application);

        Notification notification = notificationRepository.findById(createAppReqDTO.notificationId())
                .orElseThrow(() -> new NotificationNotFoundException(ApplicationMessageCode.NOTIFICATION_NOT_FOUND));

        Match match = getMatch(application, notification);

        matchRepository.save(match);


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
