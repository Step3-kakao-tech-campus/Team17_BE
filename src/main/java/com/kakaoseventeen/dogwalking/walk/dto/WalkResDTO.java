package com.kakaoseventeen.dogwalking.walk.dto;

import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class WalkResDTO {

    private WalkResDTO(){
    }

    @Getter @Setter
    public static class WalkStatus {

        private String walkStatusField;

        public WalkStatus(Walk walk) {
            this.walkStatusField = walk.getWalkStatus().toString();
        }
    }

    @Getter @Setter
    public static class StartWalk {
        private Long id;

        private String walkStatus;

        private LocalDateTime startTime;

        public StartWalk(Walk walk) {
            this.id = walk.getId();
            this.walkStatus = walk.getWalkStatus().toString();
            this.startTime = walk.getStartTime();
        }
    }

    @Getter @Setter @Builder
    public static class EndWalk {

        private Long userId;
        private Long receiveMemberId;
        private String profile;

        private Long walkId;
        private LocalDateTime walkStartTIme;
        private LocalDateTime walkEndTime;

        private Long notificationId;
        private BigDecimal coin;

        public static EndWalk of(Member member, Member receiver, Walk walk, Notification notification){
            return EndWalk.builder()
                    .userId(member.getId())
                    .receiveMemberId(receiver.getId())
                    .profile(member.getProfileImage())
                    .walkId(walk.getId())
                    .walkStartTIme(walk.getStartTime())
                    .walkEndTime(walk.getEndTime())
                    .notificationId(notification.getId())
                    .coin(notification.getCoin())
                    .build();
        }
    }

    @Getter @Setter
    public static class FindByUserId {

        private List<WalkStatusDTO> walkStatusDTOS;

        public FindByUserId (List<Walk> walks){
            this.walkStatusDTOS = walks.stream().map(WalkStatusDTO::new).toList();
        }

        @Getter @Setter
        public static class WalkStatusDTO {

            private Long id;

            private String walkStatus;

            public WalkStatusDTO(Walk walk) {
                this.id = walk.getId();
                this.walkStatus = walk.getWalkStatus().toString();
            }
        }
    }


    @Getter @Setter
    public static class FindNotEndWalksByUserId {

        private List<NotReviewedWalkDTO> walkStatusDTOS;

        public FindNotEndWalksByUserId (List<Walk> walks, Long memberId){
            this.walkStatusDTOS = walks.stream().map(walk -> new NotReviewedWalkDTO(walk, memberId)).toList();
        }

        @Getter @Setter
        public static class NotReviewedWalkDTO {

            private Long userId;

            private Long receiveMemberId;

            private Long walkId;

            private String walkStatus;

            private Long notificationId;

            private boolean isReviewed;

            private boolean isMaster;

            public NotReviewedWalkDTO(Walk walk, Long memberId) {
                this.userId = walk.getMaster().getId();
                this.receiveMemberId = walk.getMaster().getId();
                this.walkId = walk.getId();
                this.walkStatus = walk.getWalkStatus().toString();
                this.notificationId = walk.getNotification().getId();
                this.isReviewed = walk.isReviewed();
                this.isMaster = walk.getMaster().getId().equals(memberId);
            }
        }
    }
}
