package com.kakaoseventeen.dogwalking.dog.repository;

import com.kakaoseventeen.dogwalking.dog.domain.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DogJpaRepository extends JpaRepository<Dog, Long> {

    List<Dog> findDogsByMemberId(long memberId);
}
