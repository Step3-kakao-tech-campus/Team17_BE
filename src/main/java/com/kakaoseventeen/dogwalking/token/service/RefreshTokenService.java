package com.kakaoseventeen.dogwalking.token.service;

import com.kakaoseventeen.dogwalking._core.utils.MemberMessageCode;
import com.kakaoseventeen.dogwalking._core.utils.exception.member.RefreshTokenExpiredException;
import com.kakaoseventeen.dogwalking._core.utils.exception.member.RefreshTokenNotExistException;
import com.kakaoseventeen.dogwalking.token.domain.RefreshToken;
import com.kakaoseventeen.dogwalking.token.dto.RefreshRespDTO;
import com.kakaoseventeen.dogwalking.token.repository.RefreshTokenJpaRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.kakaoseventeen.dogwalking._core.security.JwtProvider.accessTokenValidTime;
import static com.kakaoseventeen.dogwalking._core.security.JwtProvider.createKey;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final RefreshTokenJpaRepository refreshTokenJpaRepository;

    /**
     * Refresh Token의 유효기간이 만료되었는지 검증
     */
    private static boolean isNotExpired(Jws<Claims> claims) {
        return !claims.getBody().getExpiration().before(new Date());
    }

    /**
     * Access Token 재발급 메서드
     */
    public String recreationAccessToken(String email, Object id) {

        Claims claims = Jwts.claims().setSubject(String.valueOf(email));
        claims.put("id", id);

        String accessToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidTime))
                .setIssuedAt(new Date())
                .signWith(createKey(), SignatureAlgorithm.HS512)
                .compact();

        return accessToken;
    }

    /**
     * 새로운 Access Token 발급 메서드
     */
    public RefreshRespDTO refresh(String refreshToken) throws RuntimeException {

        //클라이언트에게 받은 refresh token값 db에서 찾기
        RefreshToken refreshTokenDB = refreshTokenJpaRepository.findByToken(refreshToken).orElseThrow(
                () -> new RefreshTokenNotExistException(MemberMessageCode.REFRESH_TOKEN_NOT_EXIST)
        );

        //db에서 찾은 refresh token값
        String token = refreshTokenDB.getToken();

        //refresh token 검증을 한다.
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(createKey())
                    .build()
                    .parseClaimsJws(token);

            //refresh 토큰의 만료시간이 지나지 않았을 경우, 새로운 access 토큰을 생성
            if (isNotExpired(claims)) {
                String accessToken = recreationAccessToken(claims.getBody().get("sub").toString(), claims.getBody().get("id"));
                return new RefreshRespDTO(accessToken);
            }
        } catch (Exception e) {
            log.warn("토큰이 만료되었습니다");
            throw new RefreshTokenExpiredException(MemberMessageCode.REFRESH_TOKEN_EXPIRED);
        }

		return null;
    }
}

