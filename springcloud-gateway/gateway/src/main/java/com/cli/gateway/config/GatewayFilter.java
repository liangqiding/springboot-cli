package com.cli.gateway.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.common.utils.StringUtils;
import com.cli.gateway.utils.RequestUtils;
import com.cli.gateway.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * 网关拦截
 *
 * @author ding
 */
@Component
@Slf4j
public class GatewayFilter implements GlobalFilter, Ordered {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String url = request.getURI().getPath();
        log.info("接收到请求：{}", url);
        // 跨域放行
        if (request.getMethod() == HttpMethod.OPTIONS) {
            response.setStatusCode(HttpStatus.OK);
            return Mono.empty();
        }
        // 授权
        if (!this.auth(exchange, chain)) {
            return this.responseBody(exchange, 406, "请先登录");
        }
        return chain.filter(exchange);
    }

    /**
     * 认证
     */
    private boolean auth(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 逻辑自行实现
        String token = this.getToken(exchange.getRequest());
        log.info("token:{}", token);
        return true;
    }

    /**
     * 获取token
     */
    public String getToken(ServerHttpRequest request) {
        String token = request.getHeaders().getFirst("token");
        if (StringUtils.isBlank(token)) {
            return request.getQueryParams().getFirst("token");
        }
        return token;
    }

    /**
     * 设置响应体
     **/
    public Mono<Void> responseBody(ServerWebExchange exchange, Integer code, String msg) {
        String message = JSON.toJSONString(new ResponseResult<>(code, msg));
        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        return this.responseHeader(exchange).getResponse()
                .writeWith(Flux.just(exchange.getResponse().bufferFactory().wrap(bytes)));
    }

    /**
     * 设置响应体的请求头
     */
    public ServerWebExchange responseHeader(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");
        return exchange.mutate().response(response).build();
    }

    @Override
    public int getOrder() {
        return -100;
    }

}
