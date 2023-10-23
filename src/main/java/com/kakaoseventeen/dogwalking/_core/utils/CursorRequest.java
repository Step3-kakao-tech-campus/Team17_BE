package com.kakaoseventeen.dogwalking._core.utils;

import lombok.Getter;

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