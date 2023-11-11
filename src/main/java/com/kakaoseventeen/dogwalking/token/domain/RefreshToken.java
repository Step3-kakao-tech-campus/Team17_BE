package com.kakaoseventeen.dogwalking.token.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Refresh Token 엔티티
 *
 * @author 곽민주
 * @version 1.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="refreshtoken_tb")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String token;
    @Column
    private String email;
    @Builder
    public RefreshToken(int id, String token, String email){
        this.id = id;
        this.token = token;
        this.email = email;
    }
}
