package com.kakaoseventeen.dogwalking.member.service;

import com.kakaoseventeen.dogwalking._core.security.JwtProvider;
import com.kakaoseventeen.dogwalking._core.utils.MemberMessageCode;
import com.kakaoseventeen.dogwalking._core.utils.exception.DuplicateEmailException;
import com.kakaoseventeen.dogwalking._core.utils.exception.InvalidEmailFormatException;
import com.kakaoseventeen.dogwalking._core.utils.exception.InvalidPasswordFormatException;
import com.kakaoseventeen.dogwalking._core.utils.exception.InvalidPasswordLengthException;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.dto.*;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;
import com.kakaoseventeen.dogwalking.token.domain.RefreshToken;
import com.kakaoseventeen.dogwalking.token.repository.RefreshTokenJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    private final Validator validator;

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

    @Transactional
    public void signup(SignupReqDTO signupReqDTO){
        validSignupDTO(signupReqDTO);

        Member member = signupReqDtoToMember(signupReqDTO);

        memberJpaRepository.save(member);
    }

    private void validSignupDTO(SignupReqDTO signupReqDTO) {
        if(!validator.validEmailFormat(signupReqDTO.email())){
            throw new InvalidEmailFormatException(MemberMessageCode.INVALID_EMAIL_FORMAT);
        }
        duplicateEmail(signupReqDTO.email());
        if(!validator.checkPasswordLength(signupReqDTO.password())){
            throw new InvalidPasswordLengthException(MemberMessageCode.INVALID_PASSWORD_LENGTH);
        }
        if(!validator.validPasswordFormat(signupReqDTO.password())){
            throw new InvalidPasswordFormatException(MemberMessageCode.INVALID_PASSWORD_FORMAT);
        }
    }

    private void duplicateEmail(String email) {
        if(memberJpaRepository.existsByEmail(email)){
            throw new DuplicateEmailException(MemberMessageCode.DUPLICATE_EMAIL);
        }
    }

    private Member signupReqDtoToMember(SignupReqDTO signupReqDTO) {
        return Member.builder()
                .nickname(signupReqDTO.nickname())
                .email(signupReqDTO.email())
                .password(passwordEncoder.encode(signupReqDTO.password()))
                .build();
    }
}
