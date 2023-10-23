package com.kakaoseventeen.dogwalking.walkRoad.service;


import com.kakaoseventeen.dogwalking._core.utils.MessageCode;
import com.kakaoseventeen.dogwalking._core.utils.exception.WalkNotExistException;
import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import com.kakaoseventeen.dogwalking.walk.repository.WalkRepository;
import com.kakaoseventeen.dogwalking.walkRoad.domain.WalkRoad;
import com.kakaoseventeen.dogwalking.walkRoad.dto.WalkRoadReqDTO;
import com.kakaoseventeen.dogwalking.walkRoad.dto.WalkRoadRespDTO;
import com.kakaoseventeen.dogwalking.walkRoad.repository.WalkRoadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    private final WalkRepository walkRepository;

    /**
     * 산책 경로 저장하기 메서드
     */
    @Transactional
    public WalkRoadRespDTO.SaveWalkResp saveWalkRoad(WalkRoadReqDTO walkRoadReqDTO, Long walkId) throws WalkNotExistException {
        Walk walk = walkRepository.findById(walkId).orElseThrow(() -> new WalkNotExistException(MessageCode.WALK_NOT_EXIST));

        WalkRoad walkRoad = walkRoadRepository.save(WalkRoad.of(walkRoadReqDTO.getLat(), walkRoadReqDTO.getLng(), walk));
        return new WalkRoadRespDTO.SaveWalkResp(walkRoad);
    }

    @Transactional(readOnly = true)
    public WalkRoadRespDTO.FindByWalkId findAllByWalkId(Long walkId) throws WalkNotExistException {
        Walk walk = walkRepository.findById(walkId).orElseThrow(() -> new WalkNotExistException(MessageCode.WALK_NOT_EXIST));

        List<WalkRoad> walks = walkRoadRepository.findWalkRoadsByWalk(walk.getId());
        return new WalkRoadRespDTO.FindByWalkId(walks);
    }
}
