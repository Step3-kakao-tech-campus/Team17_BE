package com.kakaoseventeen.dogwalking.walkRoad.repository;

import com.kakaoseventeen.dogwalking.walkRoad.domain.WalkRoad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * WalkRoad(산책 경로) 레포지토리
 *
 * @author 승건 이
 * @version 1.0
 */
public interface WalkRoadRepository extends JpaRepository<WalkRoad, Long> {

    /**
     * walkId를 통해서 WalkRoad 엔티티들을 createdAt을 기준으로 오름차순으로 가져오는 쿼리
     */
    @Query("select wr from WalkRoad wr join fetch wr.walk where wr.walk.id = :walkId order by wr.createdAt asc")
    List<WalkRoad> findWalkRoadsByWalk(Long walkId);
}
