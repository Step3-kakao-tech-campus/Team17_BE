package com.kakaoseventeen.dogwalking.member.service;

import com.kakaoseventeen.dogwalking._core.security.CustomUserDetails;
import com.kakaoseventeen.dogwalking._core.security.JwtProvider;
import com.kakaoseventeen.dogwalking._core.utils.MemberMessageCode;
import com.kakaoseventeen.dogwalking._core.utils.S3Uploader;
import com.kakaoseventeen.dogwalking._core.utils.exception.*;
import com.kakaoseventeen.dogwalking._core.utils.MessageCode;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.kakaoseventeen.dogwalking._core.utils.MemberMessageCode.MEMBER_NOT_EXIST;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberJpaRepository memberJpaRepository;
    private final RefreshTokenJpaRepository refreshTokenJpaRepository;

    private final PasswordEncoder passwordEncoder;

    private final Validator validator;

    private final S3Uploader s3Uploader;

    private final DogJpaRepository dogJpaRepository;
    private final ApplicationRepository applicationRepository;
    private final NotificationJpaRepository notificationJpaRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public LoginRespDTO login(LoginReqDTO loginReqDTO) throws RuntimeException{
        //회원가입이 되어있는 유저인지 확인
        Member member = memberJpaRepository.findByEmail(loginReqDTO.getEmail()).orElseThrow(
                () -> new MemberNotExistException(MEMBER_NOT_EXIST)
        );

        if(!passwordEncoder.matches(loginReqDTO.getPassword(), member.getPassword())){
            throw new PasswordNotMatchException(MemberMessageCode.PASSWORD_NOT_MATCH);
        }


        String email = member.getEmail();

        //accessToken과 refreshToken을 발급받아서 response dto에 담는다.
        LoginRespDTO loginRespDTO = JwtProvider.createToken(member);

        //refresh token 객체를 만든다.
        RefreshToken refreshToken = RefreshToken.builder()
                .token(loginRespDTO.getRefreshToken())
                .email(email)
                .build();

        //이미 로그인을 했었던 유저라면(db에 refresh token이 존재함) 기존 refresh token 삭제
        if (refreshTokenJpaRepository.existsByEmail(email)){
            refreshTokenJpaRepository.deleteByEmail(email);
        }
        // 새로 발급한 refresh token 테이블에 저장
        refreshTokenJpaRepository.save(refreshToken);

        return loginRespDTO;
    }

    /**
     * 프로필 수정 메서드
     */
    @Transactional
    public UpdateProfileRespDTO updateProfile(CustomUserDetails customUserDetails, MultipartFile profileImage, String profileContent) throws IOException, MemberNotExistException {
        Member member = memberJpaRepository.findById(customUserDetails.getMember().getId())
                .orElseThrow(() ->  new MemberNotExistException(MEMBER_NOT_EXIST));

        if (profileImage != null){
            String userProfile = s3Uploader.uploadFiles(member.getId(), profileImage, "userProfile");
            member.updateProfile(userProfile, profileContent);
        } else {
            member.updateProfile(null, profileContent);
        }

        return new UpdateProfileRespDTO(member);
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
    @Transactional(readOnly = true)
    public IsOwnerRespDTO isProfileOwner(CustomUserDetails customUserDetails, Long userId) throws RuntimeException{
        Member member =  memberJpaRepository.findById(userId).orElseThrow(() -> new RuntimeException("잘못된 유저 ID 입니다."));
        return new IsOwnerRespDTO(customUserDetails.getMember().getId().equals(member.getId()));
    }

    /**
     * userId를 통한 프로필 조회 메서드
     */
    @Transactional(readOnly = true)
    public MemberProfileRespDTO getProfile(CustomUserDetails customUserDetails, Long userId) throws MemberNotExistException{
        // 여기서 userId가 null이면 본인의 프로필
        if (userId == null){
            return respProfile(customUserDetails.getMember());
        }

        Member member =  memberJpaRepository.findById(userId).orElseThrow(() -> new MemberNotExistException(MEMBER_NOT_EXIST));
        return respProfile(member);
    }

    private MemberProfileRespDTO respProfile(Member member) throws MemberNotExistException {

        List<Dog> dogs = dogJpaRepository.findDogsByMember(member.getId());

        List<Notification> notifications = notificationJpaRepository.findNotificationByMemberId(member.getId());

        List<Application> applications = applicationRepository.findApplicationByMemberId(member.getId());

        List<Review> reviews = reviewRepository.findReviewByMemberId(member.getId());

        return MemberProfileRespDTO.of(member, notifications, dogs, applications, reviews);
    }
}
