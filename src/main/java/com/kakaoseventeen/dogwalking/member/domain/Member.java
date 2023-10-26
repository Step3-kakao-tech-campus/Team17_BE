package com.kakaoseventeen.dogwalking.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="member_tb")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 45, nullable = false)
    private String nickname;
    @Column(length = 100, nullable = false, unique = true)
    private String email;
    @Column(length = 256, nullable = false)
    private String password;
    @Column (length = 256)
    private String profileImage;
    @Column (length = 256)
    private String profileContent;
    @ColumnDefault("50")
    private int dogBowl;
    private BigDecimal coin;

    @Builder
    public Member(Long id, String nickname, String email, String password, String profileImage, String profileContent, int dogBowl, BigDecimal coin){
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.profileImage = profileImage;
        this.profileContent = profileContent;
        this.dogBowl = dogBowl;
        this.coin = coin;
    }

    public void updateProfile(String profileImage, String profileContent){
        if (profileImage != null) {
            this.profileImage = profileImage;
        }
        if (profileContent != null) {
            this.profileContent = profileContent;
        }
    }

    /**
     * 코인 출금하는 메서드
     */
    public void withdrawCoin(BigDecimal coin){
        this.coin = this.coin.subtract(coin);
        System.out.println("현재 코인값은 " + this.coin); // 정상 연산된다. -> 더티 체킹이 안되는 듯
    }

    /**
     * 코인 입금받는 메서드
     */
    public void depositCoin(BigDecimal coin){
        this.coin = this.coin.add(coin);
        System.out.println("현재 코인값은 " + this.coin);
    }
}
