package com.kakaoseventeen.dogwalking.match.dto;

import com.kakaoseventeen.dogwalking.application.domain.Application;
import com.kakaoseventeen.dogwalking.match.domain.Match;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MatchResDTO {

    private List<MatchListDTO> matchList;

    public MatchResDTO(List<Match> matchList) {
		        this.matchList = matchList.stream()
                .map(match -> new MatchListDTO(match, match.getApplicationId()))
                .collect(Collectors.toList());
    }



	@Getter
    public static class MatchListDTO {
        private Long matchId;
        private Long notiMemberId;
        private String certification;
        private String experience;
        private MemberDTO member;


        public MatchListDTO(Match match, Application application){
            this.matchId = match.getMatchId();
            this.notiMemberId = match.getNotificationId().getDog().getMember().getId();
            this.certification = application.getCertification();
            this.experience = application.getExperience();
			this.member = new MemberDTO(
                    application.getAppMemberId().getId(),
                    application.getAppMemberId().getNickname(),
                    application.getAppMemberId().getProfileImage()
            );
        }
    }

    @Getter
    public static class MemberDTO{
        private Long appMemberId;
        private String username;
        private String image;

		public MemberDTO(Long appMemberId, String username, String image) {
            this.appMemberId = appMemberId;
            this.username = username;
            this.image = image;
        }
    }


}
