package com.kakaoseventeen.dogwalking.walkRoad.dto;

import com.kakaoseventeen.dogwalking.walkRoad.domain.WalkRoad;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class WalkRoadRespDTO {

    @Getter @Setter
    public static class SaveWalkResp{
        private double lat;
        private double lng;

        public SaveWalkResp(WalkRoad walkRoad){
            this.lat = walkRoad.getLat();
            this.lng = walkRoad.getLng();
        }
    }

    @Getter @Setter
    public static class FindByWalkId{

        private List<WalkRoadLatLngDTO> walkRoadLatLngDTOS;

        public FindByWalkId(List<WalkRoad> walkRoads){
            this.walkRoadLatLngDTOS = walkRoads.stream().map(WalkRoadLatLngDTO::new).collect(Collectors.toList());
        }

        @Getter @Setter
        public class WalkRoadLatLngDTO {

            private Long id;
            private double lat;
            private double lng;

            public WalkRoadLatLngDTO(WalkRoad walkRoad) {
                this.id = walkRoad.getId();
                this.lat = walkRoad.getLat();
                this.lng = walkRoad.getLng();
            }
        }
    }
}
