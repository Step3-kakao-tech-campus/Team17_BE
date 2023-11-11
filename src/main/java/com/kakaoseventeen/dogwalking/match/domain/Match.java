package com.kakaoseventeen.dogwalking.match.domain;

import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import com.kakaoseventeen.dogwalking.application.domain.Application;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Match(매칭) 엔티티
 *
 * @author 승건 이
 * @version 1.0
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@Builder
@Table(name = "MATCH_tb")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Notification notificationId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Application applicationId;
    @Column(nullable = false)
    private boolean isSuccess;
    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    /**
     * 매칭 성공 여부를 업데이트하는 메서드
     */
    public void updateIsSuccess(boolean isSuccess){
        this.isSuccess=isSuccess;
    }
}
