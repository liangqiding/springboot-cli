package com.springboot.cli.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;


/**
 * token管理
 *
 * @author Ding
 */
@Slf4j
public class TokenProvider {

    /**
     * 盐
     */
    private static final String SALT_KEY = "XXX";

    /**
     * 令牌有效期秒
     */
    private static final long TOKEN_VALIDITY = 60 * 60 * 24 * 15;

    /**
     * Base64 密钥
     */
    private final static String SECRET_KEY = Base64.getEncoder().encodeToString(SALT_KEY.getBytes(StandardCharsets.UTF_8));


    /**
     * 生成token
     *
     * @param userId 用户id
     */
    public static String createToken(Object userId) {
        return createToken(userId, "");
    }

    /**
     * 生成token
     *
     * @param userId   用户id
     * @param clientId 用于区别客户端，如移动端，网页端，此处可根据自己业务自定义
     */
    public static String createToken(Object userId, String clientId) {
        Date validity = new Date((new Date()).getTime() + TOKEN_VALIDITY * 1000);
        return Jwts.builder()
                // 代表这个JWT的主体，即它的所有人
                .setSubject(String.valueOf(userId))
                // 代表这个JWT的签发主体
                .setIssuer("")
                // 是一个时间戳，代表这个JWT的签发时间；
                .setIssuedAt(new Date())
                // 代表这个JWT的接收对象
                .setAudience(clientId)
                // 放入用户id
                .claim("userId", userId)
                // 自定义信息
                .claim("xx", "")
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setExpiration(validity)
                .compact();
    }

    /**
     * 校验token
     */
    public static boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            log.error("无效的token：" + authToken);
        }
        return false;
    }

    /**
     * 解码token
     */
    public static Claims decodeToken(String token) {
        if (validateToken(token)) {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
            // 客户端id
            String clientId = claims.getAudience();
            // 用户id
            Object userId = claims.get("userId");
            log.info("token有效,userId:{}", userId);
            return claims;
        }
        log.error("***token无效***");
        return null;
    }
}
