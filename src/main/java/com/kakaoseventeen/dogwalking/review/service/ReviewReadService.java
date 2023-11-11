package com.kakaoseventeen.dogwalking.review.service;

import com.kakaoseventeen.dogwalking._core.utils.ReviewMessageCode;
import com.kakaoseventeen.dogwalking._core.utils.exception.review.ReviewMemberNotFoundException;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.repository.MemberRepository;
import com.kakaoseventeen.dogwalking.review.dto.GetReviewResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewReadService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public GetReviewResDTO getReviewPreview(Long memberId){


        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ReviewMemberNotFoundException(ReviewMessageCode.REVIEW_MEMBER_NOT_FOUND));

        return GetReviewResDTO.builder()
                .memberNickname(member.getNickname())
                .memberImage(member.getProfileImage())
                .build();

    }

}
