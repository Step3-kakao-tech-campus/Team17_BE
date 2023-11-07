package com.kakaoseventeen.dogwalking.review.service;

import com.kakaoseventeen.dogwalking._core.utils.ReviewMessageCode;
import com.kakaoseventeen.dogwalking._core.utils.exception.review.ReviewMemberNotFoundException;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberJpaRepository;
import com.kakaoseventeen.dogwalking.review.dto.GetReviewResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewReadService {

    private final MemberJpaRepository memberJpaRepository;

    public GetReviewResDTO getReviewPrivew(Long memberId){


        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new ReviewMemberNotFoundException(ReviewMessageCode.REVIEW_MEMBER_NOT_FOUND));

        return GetReviewResDTO.builder()
                .memberNickname(member.getNickname())
                .memberImage(member.getProfileImage())
                .build();

    }

}
