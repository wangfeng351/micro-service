package com.soft1851.spring.cloud.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/10/13
 * @Version 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class JwtOperator {
    /**
     * 绉橀挜
     */
    @Value("${secret:aaaaaaabbbbbbcccccdddddaaaaaaabbbbbbcccccdddddaaaaaaabbbbbbcccccddddd}")
    private String secret;
    /**
     * 鏈夋晥鏈燂紝鍗曚綅绉�
     * 榛樿2鍛�
     */
    @Value("${expire-time-in-second:1209600}")
    private Long expirationTimeInSecond;

    /**
     * 浠巘oken涓幏鍙朿laim
     *
     * @param token token
     * @return claim
     */
    public Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(this.secret.getBytes())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            log.error("token瑙ｆ瀽閿欒", e);
            throw new IllegalArgumentException("Token invalided.");
        }
    }

    /**
     * 鑾峰彇token鐨勮繃鏈熸椂闂�
     *
     * @param token token
     * @return 杩囨湡鏃堕棿
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token)
                .getExpiration();
    }

    /**
     * 鍒ゆ柇token鏄惁杩囨湡
     *
     * @param token token
     * @return 宸茶繃鏈熻繑鍥瀟rue锛屾湭杩囨湡杩斿洖false
     */
    private Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 璁＄畻token鐨勮繃鏈熸椂闂�
     *
     * @return 杩囨湡鏃堕棿
     */
    public Date getExpirationTime() {
        return new Date(System.currentTimeMillis() + this.expirationTimeInSecond * 1000);
    }

    /**
     * 涓烘寚瀹氱敤鎴风敓鎴恡oken
     *
     * @param claims 鐢ㄦ埛淇℃伅
     * @return token
     */
    public String generateToken(Map<String, Object> claims) {
        Date createdTime = new Date();
        Date expirationTime = this.getExpirationTime();

        byte[] keyBytes = secret.getBytes();
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(createdTime)
                .setExpiration(expirationTime)
                // 浣犱篃鍙互鏀圭敤浣犲枩娆㈢殑绠楁硶
                // 鏀寔鐨勭畻娉曡瑙侊細https://github.com/jwtk/jjwt#features
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 鍒ゆ柇token鏄惁闈炴硶
     *
     * @param token token
     * @return 鏈繃鏈熻繑鍥瀟rue锛屽惁鍒欒繑鍥瀎alse
     */
    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    //public static void main(String[] args) {
    // 1. 鍒濆鍖�
    //JwtOperator jwtOperator = new JwtOperator();
    //jwtOperator.expirationTimeInSecond = 1209600L;
    //jwtOperator.secret = "aaaaaaabbbbbbcccccdddddaaaaaaabbbbbbcccccdddddaaaaaaabbbbbbcccccddddd";

    // 2.璁剧疆鐢ㄦ埛淇℃伅
    //HashMap<String, Object> objectObjectHashMap = Maps.newHashMap();
    //objectObjectHashMap.put("id", "1");

    // 娴嬭瘯1: 鐢熸垚token
    //String token = jwtOperator.generateToken(objectObjectHashMap);
    //// 浼氱敓鎴愮被浼艰瀛楃涓茬殑鍐呭: eyJhbGciOiJIUzI1NiJ9.eyJpZCI6IjEiLCJpYXQiOjE1NjU1ODk4MTcsImV4cCI6MTU2Njc5OTQxN30.27_QgdtTg4SUgxidW6ALHFsZPgMtjCQ4ZYTRmZroKCQ
    //System.out.println(token);

    // 灏嗚繖涓插瓧绗﹁繕鍘熶负涓婇潰鐢熸垚鐨則oken!!!
    //String someToken = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6IjEiLCJpYXQiOjE2MDI1NjkzNzQsImV4cCI6MTYwMzc3ODk3NH0.filbMWYqwMfR5fLOJwm5nb8MrRjpTzqIkuHupsUMvGE";
    //// 娴嬭瘯2: 濡傛灉鑳絫oken鍚堟硶涓旀湭杩囨湡锛岃繑鍥瀟rue
    //Boolean validateToken = jwtOperator.validateToken(someToken);
    //System.out.println(validateToken);
    ////
    ////// 娴嬭瘯3: 鑾峰彇鐢ㄦ埛淇℃伅
    //Claims claims = jwtOperator.getClaimsFromToken(someToken);
    //System.out.println(claims);
    //
    //// token鐨勭涓€娈碉紙浠�.涓鸿竟鐣岋級
    //String encodedHeader = "eyJhbGciOiJIUzI1NiJ9";
    //// 娴嬭瘯4: 瑙ｅ瘑Header
    //byte[] header = Base64.decodeBase64(encodedHeader.getBytes());
    //System.out.println(new String(header));
    //
    //// token鐨勭浜屾锛堜互.涓鸿竟鐣岋級
    //String encodedPayload = "eyJpZCI6IjEiLCJpYXQiOjE1NjU1ODk1NDEsImV4cCI6MTU2Njc5OTE0MX0";
    //// 娴嬭瘯5: 瑙ｅ瘑Payload
    //byte[] payload = Base64.decodeBase64(encodedPayload.getBytes());
    //System.out.println(new String(payload));
    //
    //// 娴嬭瘯6: 杩欐槸涓€涓绡℃敼鐨則oken锛屽洜姝や細鎶ュ紓甯革紝璇存槑JWT鏄畨鍏ㄧ殑
    //boolean flag = jwtOperator.validateToken("eyJhbGciOiJIUzI1NiJ9.eyJpZCI6IjEiLCJpYXQiOjE2MDI1NjkzNzQsImV4cCI6MTYwMzc3ODk3NH0.filbMWYqwMfR5fLddwm5nb8MrRjpTzq444uHupsUMvGE");
    //}
}
