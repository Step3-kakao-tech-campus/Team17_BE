package com.kakaoseventeen.dogwalking.member.service;

import com.kakaoseventeen.dogwalking._core.security.JwtProvider;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.dto.LoginRequestDTO;
import com.kakaoseventeen.dogwalking.member.dto.LoginResponseDTO;
import com.kakaoseventeen.dogwalking.member.dto.UpdateProfileReqDTO;
import com.kakaoseventeen.dogwalking.member.dto.UpdateProfileRespDTO;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;
import com.kakaoseventeen.dogwalking.token.domain.RefreshToken;
import com.kakaoseventeen.dogwalking.token.repository.RefreshTokenJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberJpaRepository memberJpaRepository;
    private final RefreshTokenJpaRepository refreshTokenJpaRepository;

    @Transactional
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO){

        //회원가입이 되어있는 유저인지 확인
        Member member = memberJpaRepository.findByEmail(loginRequestDTO.getEmail()).orElseThrow();

        String email = member.getEmail();

        //accessToken과 refreshToken을 발급받아서 response dto에 담는다.
        LoginResponseDTO loginResponseDTO = JwtProvider.createToken(member);

        //refresh token 객체를 만든다.
        RefreshToken refreshToken = RefreshToken.builder()
                .token(loginResponseDTO.getRefreshToken())
                .email(email)
                .build();

        //이미 로그인을 했었던 유저라면(db에 refresh token이 존재함) 기존 refresh token 삭제
        if (refreshTokenJpaRepository.existsByEmail(email)){
            refreshTokenJpaRepository.deleteByEmail(email);
        }
        // 새로 발급한 refresh token 테이블에 저장
        refreshTokenJpaRepository.save(refreshToken);

        return loginResponseDTO;
    }

    @Transactional
    public UpdateProfileRespDTO updateProfile(UpdateProfileReqDTO reqDTO, Long userId){
        Optional<Member> member = memberJpaRepository.findById(userId);

        if (member.isPresent()) {
            member.get().updateProfile(reqDTO.getProfileImage(), reqDTO.getProfileContent());
            return new UpdateProfileRespDTO(member.get());
        }
        else {
            throw new RuntimeException("올바르지 않은 유저 ID입니다.");
        }
    }
}
