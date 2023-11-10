package com.kakaoseventeen.dogwalking._core.security;

import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.dto.LoginRespDTO;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtProvider {

    private static String SECRET;
    public static final Long accessTokenValidTime = 1000L * 60 * 10;
    public static final Long refreshTokenValidTime = 1000L * 60 * 60 * 24 * 7;

    private final CustomUserDetailsService customUserDetailsService;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        SECRET = secret;
    }

    public static Key createKey() {
        byte[] apiKeySecretBytes = Base64.getDecoder().decode(SECRET);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS512.getJcaName());
    }

    public static LoginRespDTO createToken(Member member) {

        Claims claims = Jwts.claims().setSubject(String.valueOf(member.getEmail()));
        claims.put("id", member.getId());

        String accessToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidTime))
                .setIssuedAt(new Date())
                .signWith(createKey(), SignatureAlgorithm.HS512)
                .compact();

        String refreshToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenValidTime))
                .signWith(createKey(), SignatureAlgorithm.HS512)
                .compact();

        return new LoginRespDTO(accessToken, refreshToken);
    }

    // 헤더의 access 토큰의 유효성 + 만료일자 확인
    public boolean isTokenValidate(String accessToken) {
        try {
            //JWT 토큰 파싱 및 검증을 시도
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(createKey())
                    .build()
                    .parseClaimsJws(accessToken);
            // 토큰의 만료 시간을 현재 시간과 비교하여 유효성을 확인
            // 만료 시간이 현재 시간 이후라면 토큰은 유효함
            return !claims.getBody().getExpiration().before(new Date());
        } catch (SecurityException e) {
            log.error("Invalid JWT signature.");
            throw new JwtException("잘못된 JWT 형식입니다.");
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token.");
            throw new JwtException("유효하지 않은 토큰입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token.");
            throw new JwtException("토큰 기한이 만료되었습니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token.");
            throw new JwtException("지원하지 않는 JWT 토큰입니다.");
        }
    }

    //JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(createKey())
                .build()
                .parseClaimsJws(token);

        Long idLong = Long.parseLong(claims.getBody().get("id").toString());

        CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(claims.getBody().getSubject());
        //인증용 객체
        return new UsernamePasswordAuthenticationToken(customUserDetails, token, customUserDetails.getAuthorities());
    }
}
