package com.kakaoseventeen.dogwalking.dog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DogJpaRepository extends JpaRepository<Dog, Integer> {

    List<Dog> findDogsByMemberId(long memberId);
}
