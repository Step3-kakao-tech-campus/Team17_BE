package com.kakaoseventeen.dogwalking.review.service;

import com.kakaoseventeen.dogwalking._core.utils.ReviewMessageCode;
import com.kakaoseventeen.dogwalking._core.utils.exception.review.MemberIdNotExistException;
import com.kakaoseventeen.dogwalking._core.utils.exception.review.NotificationIdNotExistException;
import com.kakaoseventeen.dogwalking._core.utils.exception.review.ReceiveMemberIdNotExistException;
import com.kakaoseventeen.dogwalking._core.utils.exception.review.ReviewContentNotExistException;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;
import com.kakaoseventeen.dogwalking.notification.repository.NotificationJpaRepository;
import com.kakaoseventeen.dogwalking.review.domain.Review;
import com.kakaoseventeen.dogwalking.review.dto.WriteReviewReqDTO;
import com.kakaoseventeen.dogwalking.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewWriteService {
    private final ReviewRepository reviewRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final NotificationJpaRepository notificationJpaRepository;

    /**
     *
     * @param writeReviewReqDTO
     * @apiNote 리뷰 작성 요청을 받아 DB에 저장한다.
     */
    public void writeReview(WriteReviewReqDTO writeReviewReqDTO){
        // 객체 유효성 검사 Validator
        validator(writeReviewReqDTO);

        // 리뷰 받는 사람이 견주인지 확인
        // Notification -> Dog -> Member
        Member dogOwner = notificationJpaRepository.mfindMember(writeReviewReqDTO.notificationId()).getDog().getMember();
                //.orElseThrow(
                //() -> new NotificationIdNotExistException(ReviewMessageCode.NOTIFICATION_ID_NOT_EXIST)
        //);
        boolean isReceiverDogOwner = Objects.equals(dogOwner.getId(), writeReviewReqDTO.receiveMemberId());

        // 리뷰하는 사람, 리뷰 받는 사람 조회
        Member receiveMember = memberJpaRepository.findById(writeReviewReqDTO.receiveMemberId()).orElseThrow(
                () -> new ReceiveMemberIdNotExistException(ReviewMessageCode.RECEIVE_MEMBER_ID_NOT_EXIST)
        );
        // TODO - Security에서 이미 검증하는 것이라면 삭제할 예정
        Member member = memberJpaRepository.findById(writeReviewReqDTO.memberId()).orElseThrow(
                () -> new MemberIdNotExistException(ReviewMessageCode.MEMBER_ID_NOT_EXIST)
        );
        // DTO를 Review Entity로 변환
        Review review = dtoToReview(writeReviewReqDTO, member, receiveMember, isReceiverDogOwner);

        // 변환된 리뷰 엔티티 저장
        reviewRepository.save(review);

    }

    private Review dtoToReview(WriteReviewReqDTO writeReviewReqDTO, Member memeber, Member receiveMember, boolean isReceiverDogOwner) {
        return Review.builder()
                .reviewerId(memeber)
                .receiverId(receiveMember)
                .reviewEval1(writeReviewReqDTO.reviewEval().eval1())
                .reviewEval2(writeReviewReqDTO.reviewEval().eval2())
                .reviewEval3(writeReviewReqDTO.reviewEval().eval3())
                .reviewEval4(writeReviewReqDTO.reviewEval().eval4())
                .reviewContent(writeReviewReqDTO.reviewContent())
                .dogBowl(writeReviewReqDTO.dogBowl())
                .isReceiverDogOwner(isReceiverDogOwner)
                .build();

    }

    // TODO - 추후 클래스로 분리할 것
    private void validator(WriteReviewReqDTO writeReviewReqDTO) {

        // reviewContent가 비어있는가
        if(writeReviewReqDTO.reviewContent().isEmpty()){
            throw new ReviewContentNotExistException(ReviewMessageCode.REVIEW_CONTENT_NOT_EXIST);
        }
    }


}
