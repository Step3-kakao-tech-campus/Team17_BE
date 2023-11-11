package com.kakaoseventeen.dogwalking.walkRoad.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * WalkRoadResDTO : 산책로 관련 요청 DTO
 *
 * @author 승건 이
 * @version 1.0
 */
@Getter
@Setter
public class WalkRoadReqDTO {
    private double lat;
    private double lng;
}
