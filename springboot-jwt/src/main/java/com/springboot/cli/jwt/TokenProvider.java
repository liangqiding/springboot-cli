package com.springboot.cli.jwt;


import io.jsonwebtoken.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;


/**
 * date: 2021-01-05 08:48
 * description token管理
 *
 * @author qiDing
 */
@Slf4j
@ApiModel("token提供者")
public class TokenProvider {

    @ApiModelProperty("盐")
    private static final String SALT_KEY = "links";

    @ApiModelProperty("令牌有效期毫秒")
    private static final long TOKEN_VALIDITY = 86400000;

    @ApiModelProperty("权限密钥")
    private static final String AUTHORITIES_KEY = "auth";

    @ApiModelProperty("Base64 密钥")
    private final static String SECRET_KEY = Base64.getEncoder().encodeToString(SALT_KEY.getBytes(StandardCharsets.UTF_8));


    /**
     * 生成token
     *
     * @param userId   用户id
     * @param clientId 用于区别客户端，如移动端，网页端，此处可根据自己业务自定义
     * @param role     角色权限
     */
    public static String createToken(String userId, String clientId, String role) {
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
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setExpiration(validity)
                .compact();
    }

    /**
     * 校验token
     */
    public static JwtUser checkToken(String token) {
        if (validateToken(token)) {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
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


    private static boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            log.error("无效的token：" + authToken);
        }
        return false;
    }
}
