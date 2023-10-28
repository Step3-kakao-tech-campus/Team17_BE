package com.kakaoseventeen.dogwalking.notification.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class UpdateNotificationReqDTO {
    @NotBlank(message = "공고글 제목은 필수 입력 사항입니다.")
    @Size(max = 30, message = "공고글 제목은 최대 30자 입니다.")
    private String title;
    @NotNull(message = "강아지를 선택해주세요")
    private Long dogId;
    @NotNull
    private Double lat;
    @NotNull
    private Double lng;
    @NotNull(message = "시작 시간을 입력해주세요")
    private LocalDateTime start;
    @NotNull(message = "종료 시간을 입력해주세요")
    private LocalDateTime end;
    private String significant;
    @NotNull(message = "코인을 입력해주세요")
    private BigDecimal coin;
}
