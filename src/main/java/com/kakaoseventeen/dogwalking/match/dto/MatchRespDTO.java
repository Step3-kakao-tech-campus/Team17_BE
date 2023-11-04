package com.kakaoseventeen.dogwalking.match.dto;

import com.kakaoseventeen.dogwalking.application.domain.Application;
import com.kakaoseventeen.dogwalking.match.domain.Match;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MatchRespDTO {

    private List<MatchListDTO> matchList;

    public MatchRespDTO(List<Match> matchList) {
		        this.matchList = matchList.stream()
                .map(
                        match -> new MatchListDTO(
                                match.getMatchId(),
                                match.getApplicationId().getCertification(),
                                match.getApplicationId().getExperience(),
                                new MemberDTO(
                                        match.getApplicationId().getAppMemberId().getNickname(),
                                        match.getApplicationId().getAppMemberId().getProfileImage()
                                )
                        )
                )
                .collect(Collectors.toList());
    }



	@Getter
    public static class MatchListDTO {
        private Long id;
        private String certification;
        private String experience;
        private MemberDTO member;


        public MatchListDTO(Long id, String certification, String experience, MemberDTO member) {
            this.id = id;
            this.certification = certification;
            this.experience = experience;
			this.member = member;
        }
    }

    @Getter
    public static class MemberDTO{
        private String username;
        private String image;

		public MemberDTO(String username, String image) {
            this.username = username;
            this.image = image;
        }
    }


}
