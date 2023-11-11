package com.kakaoseventeen.dogwalking.application.domain;

import com.kakaoseventeen.dogwalking.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

/**
 * Application (지원서) 엔티티
 *
 * @author 박영규
 * @version 1.0
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "APPLICATION_tb")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String aboutMe;
    private String certification;
    private String experience;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Member appMemberId;
    @CreatedDate
    private LocalDateTime createdAt;
}
