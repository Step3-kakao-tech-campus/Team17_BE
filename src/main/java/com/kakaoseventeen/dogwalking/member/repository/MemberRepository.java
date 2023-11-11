package com.kakaoseventeen.dogwalking.member.repository;

import com.kakaoseventeen.dogwalking.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Member(멤버) 레파지토리
 *
 * @author 곽민주
 * @version 1.0
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);
}
