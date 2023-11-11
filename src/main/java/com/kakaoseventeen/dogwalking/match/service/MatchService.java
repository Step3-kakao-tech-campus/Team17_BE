package com.kakaoseventeen.dogwalking.match.service;

import com.kakaoseventeen.dogwalking._core.utils.MessageCode;
import com.kakaoseventeen.dogwalking._core.utils.exception.MatchNotExistException;
import com.kakaoseventeen.dogwalking._core.utils.exception.notification.NotMyNotificationException;
import com.kakaoseventeen.dogwalking._core.utils.exception.notification.NotificationException;
import com.kakaoseventeen.dogwalking.match.domain.Match;
import com.kakaoseventeen.dogwalking.match.dto.MatchResDTO;
import com.kakaoseventeen.dogwalking.match.repository.MatchRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * MatchService(매칭) 서비스
 *
 * @author 곽민주
 * @version 1.0
 */
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final NotificationRepository notificationRepository;

    /**
     * 공고글에 매칭된 지원자 리스트를 응답하는 메서드
     */
    public MatchResDTO match(Long notificationId, Member sessionMember) throws RuntimeException {

        Notification notification = notificationRepository.findById(notificationId).orElseThrow(
                ()-> new NotificationException(MessageCode.NOTIFICATION_NOT_EXIST)
        );

        if(sessionMember.getId()!= notification.getDog().getMember().getId()){
            throw new NotMyNotificationException(MessageCode.NOT_MY_NOTIFICATION);
        }


        List<Match> matchList = matchRepository.mfindMatchByNotificationId(notificationId);

        if(matchList.isEmpty()) {
            throw new MatchNotExistException(MessageCode.MATCH_NOT_EXIST);
        }

        return new MatchResDTO(matchList);

    }
}
