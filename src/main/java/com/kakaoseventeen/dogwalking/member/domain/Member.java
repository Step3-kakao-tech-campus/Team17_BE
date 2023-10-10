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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
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
    public Member(int id, String nickname, String email, String password, String profileImage, String profileContent, int dogBowl, BigDecimal coin){
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.profileImage = profileImage;
        this.profileContent = profileContent;
        this.dogBowl = dogBowl;
        this.coin = coin;
    }
}
