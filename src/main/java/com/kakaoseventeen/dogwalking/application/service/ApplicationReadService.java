package com.kakaoseventeen.dogwalking.application.service;

import com.kakaoseventeen.dogwalking._core.utils.ApplicationMessageCode;
import com.kakaoseventeen.dogwalking._core.utils.exception.application.ApplicationNotFoundException;
import com.kakaoseventeen.dogwalking._core.utils.exception.application.MatchNotFoundException;
import com.kakaoseventeen.dogwalking.application.domain.Application;
import com.kakaoseventeen.dogwalking.application.dto.GetAppMemberResDTO;
import com.kakaoseventeen.dogwalking.application.dto.GetAppResDTO;
import com.kakaoseventeen.dogwalking.application.repository.ApplicationRepository;
import com.kakaoseventeen.dogwalking.match.domain.Match;
import com.kakaoseventeen.dogwalking.match.repository.MatchRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationReadService {

    private final ApplicationRepository applicationRepository;
    private final MatchRepository matchRepository;
    private final MemberRepository memberRepository;


    @Transactional(readOnly = true)
    public GetAppResDTO getApp(Long id) {

        // TODO - Custom 예외처리

        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(ApplicationMessageCode.APPLICATION_NOT_FOUND));
        Match match = matchRepository.findByApplicationId(application)
                .orElseThrow(() -> new MatchNotFoundException(ApplicationMessageCode.MATCH_NOT_FOUND));



        return GetAppResDTO.builder()
                .aboutMe(application.getAboutMe())
                .title(application.getTitle())
                .memberNickname(application.getAppMemberId().getNickname())
                .experience(application.getExperience())
                .notificationId(match.getNotificationId().getId())
                .memberImage(application.getAppMemberId().getProfileImage())
                .memberId(application.getAppMemberId().getId())
                .build();
    }

    @Transactional(readOnly = true)
    public GetAppMemberResDTO getAppMember() {
        Member member = memberRepository.findByEmail(getEmail())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다.")); // TODO - 커스텀 예외처리

        return GetAppMemberResDTO.builder()
                .memberNickname(member.getNickname())
                .memberImage(member.getProfileImage())
                .build();
    }

    private String getEmail(){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String email = loggedInUser.getName();
        return email;
    }
}
