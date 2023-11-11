package com.kakaoseventeen.dogwalking.notification.dto.request;

import com.kakaoseventeen.dogwalking.dog.domain.Dog;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * WriteNotificationReqDTO(공고글 작성 요청 DTO)
 *
 * @author 곽민주
 * @version 1.0
 */
@Getter
@Setter
public class WriteNotificationReqDTO {
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
    @NotNull(message="코인을 입력해 주세요.")
    @Min(value=1, message="코인은 최소한 1개 이상이어야 합니다.")
    private BigDecimal coin;
    private String significant;


    public Notification toEntity(Dog dog){
        return Notification.builder()
                .title(title)
                .lat(lat)
                .lng(lng)
                .startTime(start)
                .endTime(end)
                .significant(significant)
                .coin(coin)
                .dog(dog)
                .build();
    }
}
