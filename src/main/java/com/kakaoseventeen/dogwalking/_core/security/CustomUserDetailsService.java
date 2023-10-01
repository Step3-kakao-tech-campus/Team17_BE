package com.kakaoseventeen.dogwalking._core.security;

import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> memberOP = memberJpaRepository.findByEmail(email);

        if (memberOP.isEmpty()) {
            return null;
        } else {
            Member memberPS = memberOP.get();
            return new CustomUserDetails(memberPS);
        }
    }
}
