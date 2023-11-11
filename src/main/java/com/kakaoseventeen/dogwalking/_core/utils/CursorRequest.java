package com.kakaoseventeen.dogwalking._core.utils;

import lombok.Getter;

/**
 * CursorRequest : 커서 기반 페이징을 위한 요청 클래스
 *
 * @author 곽민주
 * @version 1.0
 */
@Getter
public class CursorRequest {
    public static Double NONE_KEY = -1.0;

    private Double key;
    private Integer size;

    public CursorRequest(Double key, Integer size) {
        this.key = key;
        this.size = size;
    }

    public Boolean hasSize() {
        return size != null;
    }

    public Boolean hasKey() {
        return key != null;
    }


    public CursorRequest next(Double key, Integer defaultSize) {
        return hasSize() ? new CursorRequest(key, this.size) : new CursorRequest(key, defaultSize);
    }
}