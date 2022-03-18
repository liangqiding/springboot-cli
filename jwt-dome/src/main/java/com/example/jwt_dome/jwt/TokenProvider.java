package com.example.jwt_dome.jwt;


import io.jsonwebtoken.*;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;


/**
 * date: 2021-01-05 08:48
 * description token管理
 *
 * @author qiDing
 */
@Component
@Slf4j
@ApiModel("token提供者")
public class TokenProvider {

    @ApiModelProperty("盐")
    private static final String SALT_KEY = "links";

    @ApiModelProperty("令牌有效期毫秒")
    private static final long TOKEN_VALIDITY = 86400000;

    @ApiModelProperty("权限密钥")
    private static final String AUTHORITIES_KEY = "auth";

    @ApiModelProperty("Base64 加密")
    private final Base64.Encoder encoder = Base64.getEncoder();

    @ApiModelProperty("Base64 密钥")
    private String secretKey;


    @PostConstruct
    public void init() {
        this.secretKey = encoder.encodeToString(SALT_KEY.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成token
     */
    public String createToken(String userId, String clientId, String role) {
        Date validity = new Date((new Date()).getTime() + TOKEN_VALIDITY);
        return Jwts.builder()
                // 代表这个JWT的主体，即它的所有人
                .setSubject(String.valueOf(userId))
                // 代表这个JWT的签发主体
                .setIssuer("")
                // 是一个时间戳，代表这个JWT的签发时间；
                .setIssuedAt(new Date())
                // 代表这个JWT的接收对象
                .setAudience(clientId)
                .claim("role", role)
                .claim("userId", userId)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setExpiration(validity)
                .compact();
    }

    /**
     * 校验token
     */
    public JwtUser getAuthentication(String token) {
        if (this.validateToken(token)) {
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
            String audience = claims.getAudience();
            String userId = claims.get("userId", String.class);
            String role = claims.get("role", String.class);
            JwtUser jwtUser = new JwtUser().setUserId(userId).setRole(role).setValid(true);
            log.info("===token有效{},客户端{}", jwtUser, audience);
            return jwtUser;
        }
        log.error("***token无效***");
        return new JwtUser();
    }


    private boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            log.error("无效的token：" + authToken);
        }
        return false;
    }

}
