package com.kakaoseventeen.dogwalking._core.utils;

public class CursorRequest {
    public static final Long NONE_KEY = -1L;

    private Long key;
    private Integer size;

    public CursorRequest(Long key, Integer size) {
        this.key = key;
        this.size = size;
    }

    public Boolean hasSize() {
        return size != null;
    }

    public Boolean hasKey() {
        return key != null;
    }

    public CursorRequest next(Long key, Integer defaultSize) {
        return hasSize() ? new CursorRequest(key, this.size) : new CursorRequest(key, defaultSize);
    }
}