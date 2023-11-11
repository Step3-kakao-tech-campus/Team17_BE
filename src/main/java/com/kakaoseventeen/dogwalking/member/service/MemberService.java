package com.kakaoseventeen.dogwalking.member.service;

import com.kakaoseventeen.dogwalking._core.security.CustomUserDetails;
import com.kakaoseventeen.dogwalking._core.security.JwtProvider;
import com.kakaoseventeen.dogwalking._core.utils.MemberMessageCode;
import com.kakaoseventeen.dogwalking._core.utils.S3Uploader;
import com.kakaoseventeen.dogwalking._core.utils.exception.member.*;
import com.kakaoseventeen.dogwalking.application.domain.Application;
import com.kakaoseventeen.dogwalking.application.repository.ApplicationRepository;
import com.kakaoseventeen.dogwalking.dog.domain.Dog;
import com.kakaoseventeen.dogwalking.dog.repository.DogRepository;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.dto.request.LoginReqDTO;
import com.kakaoseventeen.dogwalking.member.dto.request.SignupReqDTO;
import com.kakaoseventeen.dogwalking.member.dto.response.IsOwnerResDTO;
import com.kakaoseventeen.dogwalking.member.dto.response.LoginRespDTO;
import com.kakaoseventeen.dogwalking.member.dto.response.MemberProfileResDTO;
import com.kakaoseventeen.dogwalking.member.dto.response.UpdateProfileResDTO;
import com.kakaoseventeen.dogwalking.member.repository.MemberRepository;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.notification.repository.NotificationRepository;
import com.kakaoseventeen.dogwalking.review.domain.Review;
import com.kakaoseventeen.dogwalking.review.repository.ReviewRepository;
import com.kakaoseventeen.dogwalking.token.domain.RefreshToken;
import com.kakaoseventeen.dogwalking.token.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.kakaoseventeen.dogwalking._core.utils.MemberMessageCode.MEMBER_NOT_EXIST;

/**
 * MemberService(멤버) 서비스
 *
 * @author 곽민주, 박영규, 이승건
 * @version 1.0
 */
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final Validator validator;

    private final S3Uploader s3Uploader;

    private final DogRepository dogRepository;
    private final ApplicationRepository applicationRepository;
    private final NotificationRepository notificationRepository;
    private final ReviewRepository reviewRepository;

    /**
     * 로그인 메서드
     */
    @Transactional
    public LoginRespDTO login(LoginReqDTO loginReqDTO) throws RuntimeException{

        Member member = memberRepository.findByEmail(loginReqDTO.getEmail()).orElseThrow(
                () -> new MemberNotExistException(MEMBER_NOT_EXIST)
        );

        if(!passwordEncoder.matches(loginReqDTO.getPassword(), member.getPassword())){
            throw new PasswordNotMatchException(MemberMessageCode.PASSWORD_NOT_MATCH);
        }


        String email = member.getEmail();

        LoginRespDTO loginRespDTO = JwtProvider.createToken(member);

        RefreshToken refreshToken = RefreshToken.builder()
                .token(loginRespDTO.getRefreshToken())
                .email(email)
                .build();

        if (refreshTokenRepository.existsByEmail(email)){
            refreshTokenRepository.deleteByEmail(email);
        }

        refreshTokenRepository.save(refreshToken);

        return loginRespDTO;
    }

    /**
     * 프로필 수정 메서드
     */
    @Transactional
    public UpdateProfileResDTO updateProfile(CustomUserDetails customUserDetails, MultipartFile profileImage, String profileContent) throws IOException, MemberNotExistException {
        Member member = memberRepository.findById(customUserDetails.getMember().getId())
                .orElseThrow(() ->  new MemberNotExistException(MEMBER_NOT_EXIST));

        if (profileImage != null){
            String userProfile = s3Uploader.uploadFiles(member.getId(), profileImage, "userProfile");
            member.updateProfile(userProfile, profileContent);
        } else {
            member.updateProfile(null, profileContent);
        }

        return new UpdateProfileResDTO(member);
    }

    /**
     * 회원가입 메서드
     */
    @Transactional
    public void signup(SignupReqDTO signupReqDTO){
        validSignupDTO(signupReqDTO);

        Member member = signupReqDtoToMember(signupReqDTO);

        memberRepository.save(member);
    }

    /**
     * 이메일, 비밀번호 형식 검증 메서드
     */
    private void validSignupDTO(SignupReqDTO signupReqDTO) {
        if(!validator.validEmailFormat(signupReqDTO.email())){
            throw new InvalidEmailFormatException(MemberMessageCode.INVALID_EMAIL_FORMAT);
        }
        duplicateEmail(signupReqDTO.email());
        if(!validator.checkPasswordLength(signupReqDTO.password())){
            throw new InvalidPasswordFormatException(MemberMessageCode.INVALID_PASSWORD_LENGTH);
        }
        if(!validator.validPasswordFormat(signupReqDTO.password())){
            throw new InvalidPasswordFormatException(MemberMessageCode.INVALID_PASSWORD_FORMAT);
        }
    }

    /**
     * 중복된 이메일 검증 검증 메서드
     */
    private void duplicateEmail(String email) {
        if(memberRepository.existsByEmail(email)){
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
    /**
     * 해당 프로필이 인가된 유저 본인의 프로필인지를 판단하고, 분기처리하는 메서드
     */
    @Transactional(readOnly = true)
    public IsOwnerResDTO isProfileOwner(CustomUserDetails customUserDetails, Long userId) throws RuntimeException{
        Member member =  memberRepository.findById(userId).orElseThrow(() -> new RuntimeException("잘못된 유저 ID 입니다."));
        return new IsOwnerResDTO(customUserDetails.getMember().getId().equals(member.getId()));
    }

    /**
     * userId를 통한 프로필 조회 메서드
     */
    @Transactional(readOnly = true)
    public MemberProfileResDTO getProfile(CustomUserDetails customUserDetails, Long userId) throws MemberNotExistException{
        // 여기서 userId가 null이면 본인의 프로필
        if (userId == null){
            return respProfile(customUserDetails.getMember());
        }

        Member member =  memberRepository.findById(userId).orElseThrow(() -> new MemberNotExistException(MEMBER_NOT_EXIST));
        return respProfile(member);
    }

    /**
     * 유저 프로필 조회간 Member 엔티티를 이용해 각 Repository에 쿼리를 보내서 값을 가져오는 메서드
     */
    private MemberProfileResDTO respProfile(Member member) throws MemberNotExistException {

        List<Dog> dogs = dogRepository.findDogsByMember(member.getId());

        List<Notification> notifications = notificationRepository.findNotificationByMemberId(member.getId());

        for (Notification notification : notifications){
            log.info((notification.getWalk() != null) ? notification.getWalk().getWalkStatus().toString() : "null");
        }

        List<Application> applications = applicationRepository.findApplicationByMemberId(member.getId());

        List<Review> reviews = reviewRepository.findReviewByMemberId(member.getId());

        return MemberProfileResDTO.of(member, notifications, dogs, applications, reviews);
    }
}
