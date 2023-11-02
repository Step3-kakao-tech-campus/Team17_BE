package com.kakaoseventeen.dogwalking.dog.repository;

import com.kakaoseventeen.dogwalking.dog.domain.Dog;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DogJpaRepository extends JpaRepository<Dog, Long> {

    @Query("select d from Dog d where d.member =:member")
    List<Dog> findDogsByMember(Member member);
}
