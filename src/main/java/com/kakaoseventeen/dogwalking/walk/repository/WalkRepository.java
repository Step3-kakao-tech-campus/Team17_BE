package com.kakaoseventeen.dogwalking.walk.repository;

import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Walk(결제) 레포지토리
 *
 * @author 승건 이
 * @version 1.0
 */
public interface WalkRepository extends JpaRepository<Walk, Long> {

    /**
     * UserId를 통해서 Walk 엔티티의 WalkStatus 필드를 가져오는 쿼리
     */
    @Query("select w from Walk w where w.member.id = :userId")
    List<Walk> findByWalkStatus(Long userId);


}
