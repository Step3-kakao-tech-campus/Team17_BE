package com.kakaoseventeen.dogwalking.dog.repository;

import com.kakaoseventeen.dogwalking.dog.domain.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Dog(반려견) 레파지토리
 *
 * @author 승건 이
 * @version 1.0
 */
public interface DogRepository extends JpaRepository<Dog, Long> {

    /**
     * MemberId를 통해서 Dog 엔티티를 가져오는 쿼리
     */
    @Query("select d from Dog d where d.member.id =:memberId")
    List<Dog> findDogsByMember(@Param("memberId") Long memberId);

}
