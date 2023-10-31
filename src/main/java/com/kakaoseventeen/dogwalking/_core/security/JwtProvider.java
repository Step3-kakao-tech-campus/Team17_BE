package com.kakaoseventeen.dogwalking._core.security;

import com.kakaoseventeen.dogwalking._core.utils.MessageCode;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.member.dto.LoginResponseDTO;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    private static final String SECRET = "thisismysecretkeythisismysecretkeythisismysecretkeythisismysecretkeythisismysecretkeythisismysecretkeythisismysecretkey";
    public static final Long accessTokenValidTime = 1000L * 60 * 30;
    public static final Long refreshTokenValidTime = 1000L * 60 * 60 * 24 *7;

    private final CustomUserDetailsService customUserDetailsService;


    public static Key createKey(){
        byte[] apiKeySecretBytes = Base64.getDecoder().decode(SECRET);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS512.getJcaName());
        return signingKey;
    }

    public static LoginResponseDTO createToken(Member member){

        Claims claims = Jwts.claims().setSubject(String.valueOf(member.getEmail()));
        claims.put("id", member.getId());

        String accessToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidTime))
                .setIssuedAt(new Date())
                .signWith(createKey(),SignatureAlgorithm.HS512)
                .compact();

        String refreshToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ refreshTokenValidTime))
                .signWith(createKey(),SignatureAlgorithm.HS512)
                .compact();

        return new LoginResponseDTO(accessToken,refreshToken);
    }

    // 헤더의 access 토큰의 유효성 + 만료일자 확인
    public boolean isTokenValidate(String accessToken, HttpServletRequest request){
        try{
            //JWT 토큰 파싱 및 검증을 시도
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(createKey())
                    .build()
                    .parseClaimsJws(accessToken);
            // 토큰의 만료 시간을 현재 시간과 비교하여 유효성을 확인
            // 만료 시간이 현재 시간 이후라면 토큰은 유효함
            return !claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
        //    e.printStackTrace();
            request.setAttribute("exception", MessageCode.EXPIRED_TOKEN.getValue());
        } catch (JwtException e) {
            e.printStackTrace();
            request.setAttribute("exception", MessageCode.INVALID_TOKEN.getValue());
        }
        return false;
    }

    //JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(createKey())
                .build()
                .parseClaimsJws(token);

        Integer id = (Integer) claims.getBody().get("id");
        Long idLong = Long.valueOf(id);
        Member member = Member.builder().id(idLong).build();

        CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(claims.getBody().getSubject());
        //인증용 객체
        return new UsernamePasswordAuthenticationToken(customUserDetails,token,customUserDetails.getAuthorities());
    }


}
