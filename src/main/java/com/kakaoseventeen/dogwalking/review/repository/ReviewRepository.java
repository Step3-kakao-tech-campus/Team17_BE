package com.kakaoseventeen.dogwalking.review.repository;

import com.kakaoseventeen.dogwalking.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Review(리뷰) 레파지토리
 *
 * @author 박영규
 * @version 1.0
 */
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r " +
            "from Review r " +
            "join fetch r.receiverId " +
            "where r.receiverId.id = :userId or r.reviewerId.id =:userId")
    List<Review> findReviewByMemberId(Long userId);
}
