package com.kakaoseventeen.dogwalking.walk.repository;

import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Walk(산책 경로) 레포지토리
 *
 * @author 승건 이
 * @version 1.0
 */
public interface WalkRepository extends JpaRepository<Walk, Long> {

    /**
     * UserId를 통해서 Walk 엔티티의 WalkStatus 필드를 가져오는 쿼리
     */
    @Query("select w from Walk w where w.master.id = :userId or w.walker.id = :userId")
    List<Walk> findByWalkStatus(Long userId);

    /**
     * UserId와 산책의 상태가 END인 Walk 엔티티를 가져오는 쿼리 -> User Profile에서 사용
     * 본인이 지원한 공고 + 산책이 완료된 이력
     */
    @Query("select w from Walk w where w.walker.id = :userId or w.master.id =:userId and w.walkStatus = 'END'")
    List<Walk> findByWalkWithUserIdAndEndStatus(long userId);

    Optional<Walk> findWalkByMaster(Member member);

    /**
     * walkStatus가 END이고, 사용자나 지원자 id가 유저 id인 walk들 반환
     */
    @Query(value = "SELECT w.* " +
            "FROM walk w " +
            "LEFT JOIN notification n ON w.notification_id = n.notification_id " +
            "LEFT JOIN member_tb m ON m.id = w.master_id or m.id = w.walker_id " +
            "WHERE (w.master_id = :userId OR w.walker_id = :userId) " +
            "AND w.walk_status = 'END' " +
            "AND w.is_reviewed = 'N'", nativeQuery = true)
    List<Walk> findWalkWhatNotEnd(Long userId);

}
