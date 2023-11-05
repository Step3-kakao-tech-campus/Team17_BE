package com.kakaoseventeen.dogwalking.match.service;

import com.kakaoseventeen.dogwalking.application.domain.Application;
import com.kakaoseventeen.dogwalking.match.domain.Match;
import com.kakaoseventeen.dogwalking.match.dto.MatchRespDTO;
import com.kakaoseventeen.dogwalking.match.repository.MatchingRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MatchService {

    private final MatchingRepository matchingRepository;

    public MatchRespDTO match(Long notificationId, Member sessionMember) throws RuntimeException {

		List<Match> matchList = matchingRepository.mfindMatchByNotificationId(notificationId);

        if(matchList.isEmpty()) {
            throw new RuntimeException("매칭된 유저가 없습니다.");
        }


        return new MatchRespDTO(matchList);

    }
}
