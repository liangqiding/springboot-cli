package com.netty.client.controller;

import com.alibaba.fastjson.JSONObject;
import com.netty.client.server.TcpClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模拟发送api
 *
 * @author qiding
 */
@RequiredArgsConstructor
@RestController
@Slf4j
public class HttpApi {

    private final TcpClient tcpClient;

    /**
     * 消息发布
     */
    @GetMapping("/send")
    public String send(String message) {
        tcpClient.getSocketChannel().writeAndFlush(message);
        return "发送成功";
    }

    /**
     * 消息发布
     */
    @PostMapping("/send/json")
    public String send(@RequestBody JSONObject body) {
        tcpClient.getSocketChannel().writeAndFlush(body.toJSONString());
        return "发送成功";
    }

    /**
     * 连接
     */
    @GetMapping("connect")
    public String connect(String ip, Integer port) throws Exception {
        tcpClient.connect(ip, port);
        return "重启指令发送成功";
    }

    /**
     * 重连
     */
    @GetMapping("reconnect")
    public String reconnect() throws Exception {
        tcpClient.reconnect();
        return "重启指令发送成功";
    }
}
