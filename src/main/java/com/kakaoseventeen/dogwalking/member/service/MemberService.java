package com.kakaoseventeen.dogwalking.member.service;

import com.kakaoseventeen.dogwalking._core.security.CustomUserDetails;
import com.kakaoseventeen.dogwalking._core.security.JwtProvider;
import com.kakaoseventeen.dogwalking.application.domain.Application;
import com.kakaoseventeen.dogwalking.application.repository.ApplicationRepository;
import com.kakaoseventeen.dogwalking.dog.domain.Dog;
import com.kakaoseventeen.dogwalking.dog.repository.DogJpaRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.dto.*;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.notification.repository.NotificationJpaRepository;
import com.kakaoseventeen.dogwalking.payment.repository.PaymentRepository;
import com.kakaoseventeen.dogwalking.review.domain.Review;
import com.kakaoseventeen.dogwalking.review.repository.ReviewRepository;
import com.kakaoseventeen.dogwalking.token.domain.RefreshToken;
import com.kakaoseventeen.dogwalking.token.repository.RefreshTokenJpaRepository;
import com.kakaoseventeen.dogwalking.walk.repository.WalkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberJpaRepository memberJpaRepository;
    private final RefreshTokenJpaRepository refreshTokenJpaRepository;

    private final PaymentRepository paymentRepository;

    private final WalkRepository walkRepository;

    private final DogJpaRepository dogJpaRepository;

    private final ApplicationRepository applicationRepository;

    private final NotificationJpaRepository notificationJpaRepository;

    private final ReviewRepository reviewRepository;


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

    @Transactional(readOnly = true)
    public IsOwnerRespDTO isProfileOwner(CustomUserDetails customUserDetails, Long userId) throws RuntimeException{
        Member member =  memberJpaRepository.findById(userId).orElseThrow(() -> new RuntimeException("잘못된 유저 ID 입니다."));
        return new IsOwnerRespDTO(customUserDetails.getMember().getId().equals(member.getId()));
    }

    /**
     * userId를 통한 산책 조회 메서드
     */
    @Transactional(readOnly = true)
    public MemberProfileRespDTO getProfile(Long userId) throws RuntimeException{
         Member member = memberJpaRepository.findById(userId).orElseThrow(() -> new RuntimeException("잘못된 멤버 ID 입니다."));

         List<Dog> dogs = dogJpaRepository.findDogsByMemberId(userId);

         List<Notification> notifications = notificationJpaRepository.findNotificationByMemberId(userId);

         List<Application> applications = applicationRepository.findApplicationByMemberId(userId);

         List<Review> reviews = reviewRepository.findReviewByMemberId(userId);

         return MemberProfileRespDTO.of(member, notifications, dogs, applications, reviews);
    }
}
