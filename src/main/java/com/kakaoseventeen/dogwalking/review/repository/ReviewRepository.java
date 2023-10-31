package com.kakaoseventeen.dogwalking.review.repository;

import com.kakaoseventeen.dogwalking.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r " +
            "from Review r " +
            "where r.receiverId.id = :userId or r.reviewerId.id =:userId")
    List<Review> findReviewByMemberId(Long userId);
}
