package com.kakaoseventeen.dogwalking.walkRoad.service;


import com.kakaoseventeen.dogwalking._core.utils.MessageCode;
import com.kakaoseventeen.dogwalking._core.utils.exception.walk.WalkNotExistException;
import com.kakaoseventeen.dogwalking.match.repository.MatchRepository;
import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import com.kakaoseventeen.dogwalking.walkRoad.domain.WalkRoad;
import com.kakaoseventeen.dogwalking.walkRoad.dto.WalkRoadReqDTO;
import com.kakaoseventeen.dogwalking.walkRoad.dto.WalkRoadResDTO;
import com.kakaoseventeen.dogwalking.walkRoad.repository.WalkRoadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * WalkRoad(산책 경로) 서비스
 *
 * @author 승건 이
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class WalkRoadService {

    private final WalkRoadRepository walkRoadRepository;

    private final MatchRepository matchRepository;

    /**
     * 산책 경로 저장하기 메서드
     */
    @Transactional
    public WalkRoadResDTO.SaveWalkResp saveWalkRoad(WalkRoadReqDTO walkRoadReqDTO, Long matchId) throws WalkNotExistException {
        Walk walk = matchRepository.findWalkFromMatchById(matchId).orElseThrow(() -> new WalkNotExistException(MessageCode.WALK_NOT_EXIST));

        WalkRoad walkRoad = walkRoadRepository.save(WalkRoad.of(walkRoadReqDTO.getLat(), walkRoadReqDTO.getLng(), walk));
        return new WalkRoadResDTO.SaveWalkResp(walkRoad);
    }

    /**
     * 산책 경로 조회 메서드
     */
    @Transactional(readOnly = true)
    public WalkRoadResDTO.FindByWalkId findAllByWalkId(Long matchId) throws WalkNotExistException {
        Walk walk = matchRepository.findWalkFromMatchById(matchId).orElseThrow(() -> new WalkNotExistException(MessageCode.WALK_NOT_EXIST));

        List<WalkRoad> walks = walkRoadRepository.findWalkRoadsByWalk(walk.getId());
        return new WalkRoadResDTO.FindByWalkId(walks);
    }
}
