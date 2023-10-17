package com.kakaoseventeen.dogwalking.walk.dto;

import com.kakaoseventeen.dogwalking.walk.domain.Walk;
import com.kakaoseventeen.dogwalking.walk.domain.WalkStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class WalkRespDTO {

    @Getter @Setter
    public static class StartWalk {
        private Long id;

        private WalkStatus walkStatus;

        private LocalDateTime startTime;

        public StartWalk(Walk walk) {
            this.id = walk.getId();
            this.walkStatus = walk.getWalkStatus();
            this.startTime = walk.getStartTime();
        }
    }

    @Getter @Setter
    public static class EndWalk {
        private Long id;

        private WalkStatus walkStatus;

        public EndWalk(Walk walk) {
            this.id = walk.getId();
            this.walkStatus = walk.getWalkStatus();
        }
    }

    @Getter @Setter
    public static class FindByUserId {

        private List<WalkStatusDTO> walkStatusDTOS;

        public FindByUserId (List<Walk> walks){
            this.walkStatusDTOS = walks.stream().map(WalkStatusDTO::new).collect(Collectors.toList());
        }

        @Getter @Setter
        public class WalkStatusDTO {

            private Long id;

            private WalkStatus walkStatus;

            public WalkStatusDTO(Walk walk) {
                this.id = walk.getId();
                this.walkStatus = walk.getWalkStatus();
            }
        }
    }
}
