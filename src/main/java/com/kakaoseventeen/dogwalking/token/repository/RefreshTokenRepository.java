package com.kakaoseventeen.dogwalking.token.repository;

import com.kakaoseventeen.dogwalking.token.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Refresh Token 레파지토리
 *
 * @author 곽민주
 * @version 1.0
 */
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

    /**
     * Database에 저장된 Refresh Token을 조회하는 쿼리
     */
    @Query("select r from RefreshToken r where r.token=:refreshToken")
    Optional<RefreshToken> findByToken(@Param("refreshToken") String refreshToken);

    boolean existsByEmail(String userEmail);
    void deleteByEmail(String userEmail);
}
