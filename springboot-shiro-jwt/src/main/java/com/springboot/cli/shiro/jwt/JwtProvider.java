package com.springboot.cli.shiro.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;


/**
 * token管理
 *
 * @author Ding
 */
@Slf4j
@Component
public class JwtProvider {

    @Value("${jwt.expire}")
    private Integer expire;

    @Value("${jwt.secret}")
    private String secret;

    /**
     * 生成token
     *
     * @param userId 用户id
     */
    public String createToken(Object userId) {
        return createToken(userId, "");
    }

    /**
     * 生成token
     *
     * @param userId   用户id
     * @param clientId 用于区别客户端，如移动端，网页端，此处可根据自己业务自定义
     */
    public String createToken(Object userId, String clientId) {
        Date validity = new Date((new Date()).getTime() + expire * 1000);
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
                .signWith(SignatureAlgorithm.HS512, this.getSecretKey())
                .setExpiration(validity)
                .compact();
    }

    /**
     * 校验token
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(this.getSecretKey()).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            log.error("无效的token：" + authToken);
        }
        return false;
    }

    /**
     * 解码token
     */
    public Claims decodeToken(String token) {
        if (validateToken(token)) {
            Claims claims = Jwts.parser().setSigningKey(this.getSecretKey()).parseClaimsJws(token).getBody();
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

    private String getSecretKey() {
        return Base64.getEncoder().encodeToString(secret.getBytes(StandardCharsets.UTF_8));
    }
}
