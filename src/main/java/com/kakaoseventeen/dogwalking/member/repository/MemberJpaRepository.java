package com.kakaoseventeen.dogwalking.member.repository;

import com.kakaoseventeen.dogwalking.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByEmail(String email);
}
