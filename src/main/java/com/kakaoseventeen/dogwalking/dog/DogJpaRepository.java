package com.kakaoseventeen.dogwalking.dog;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DogJpaRepository extends JpaRepository<Dog, Long> {

    List<Dog> findDogsByMemberId(int memberId);
}
