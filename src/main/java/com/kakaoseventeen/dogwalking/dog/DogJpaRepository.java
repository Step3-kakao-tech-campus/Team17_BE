package com.kakaoseventeen.dogwalking.dog;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DogJpaRepository extends JpaRepository<Dog, Integer> {

    List<Dog> findDogsByMemberId(long memberId);
}
