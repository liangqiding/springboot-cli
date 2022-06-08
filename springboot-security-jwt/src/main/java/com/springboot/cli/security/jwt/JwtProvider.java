package com.springboot.cli.security.jwt;

import cn.hutool.jwt.JWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.util.*;


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

    public static final String TOKEN_HEADER = "token";

    public static final String AUTHORITY = "authority";

    /**
     * 生成token
     *
     * @param userId 用户id
     */
    public String createToken(Object userId) {
        return createToken(userId, null, null);
    }


    /**
     * 生成token，我们把用户id和授权信息都放入token中，
     * 无状态下每次请求都是会校验token的，这样做就不用每次校验都去查数据库了
     *
     * @param userId 用户id
     * @param roles  角色集合
     * @param auths  权限集合
     */
    public String createToken(Object userId, List<String> roles, List<String> auths) {
        ArrayList<String> authorityList = new ArrayList<>();
        // 合并角色和权限
        Optional.ofNullable(roles).ifPresent(authorityList::addAll);
        Optional.ofNullable(auths).ifPresent(authorityList::addAll);
        // 数组转String
        String authorityStr = String.join(",", authorityList);
        // 设置token有效期
        Date validity = new Date((new Date()).getTime() + expire * 1000);
        return JWT.create()
                // 秘钥
                .setKey(this.getSecretKey())
                // 代表这个JWT的主体，即它的所有人
                .setSubject(String.valueOf(userId))
                // 是一个时间戳，代表这个JWT的签发时间；
                .setIssuedAt(new Date())
                // 放入用户id
                .setPayload("userId", userId)
                // 放入角色和权限
                .setPayload(AUTHORITY, authorityStr)
                // 有效期
                .setExpiresAt(validity)
                .sign();

    }

    /**
     * 校验token
     */
    public boolean validateToken(String authToken) {
        try {
            return JWT.of(authToken).setKey(this.getSecretKey()).validate(0);
        } catch (Exception e) {
            log.error("无效的token：" + authToken);
        }
        return false;
    }

    /**
     * 解码token
     */
    public JWT decodeToken(String token) {
        if (validateToken(token)) {
            JWT jwt = JWT.of(token).setKey(this.getSecretKey());
            // 客户端id
            Object clientId = jwt.getPayload("clientId");
            // 用户id
            Object userId = jwt.getPayload("userId");
            log.info("token有效,userId:{}", userId);
            return jwt;
        }
        log.error("***token无效***");
        return null;
    }

    private byte[] getSecretKey() {
        return secret.getBytes(StandardCharsets.UTF_8);
    }
}
