package com.netty.client.controller;

import com.alibaba.fastjson.JSONObject;
import com.netty.client.server.TcpClient;
import com.netty.client.utils.MessageBuilder;
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
public class HttpProtobufApi {

    private final TcpClient tcpClient;

    /**
     * 消息发布
     */
    @GetMapping("/login")
    public String send(String username, String password) {
        tcpClient.getSocketChannel().writeAndFlush(MessageBuilder.newLogin(username, password));
        return "发送成功";
    }

    /**
     * 消息发布
     */
    @GetMapping("/send")
    public String send(Integer msgId, String data, Integer type) {
        tcpClient.getSocketChannel().writeAndFlush(MessageBuilder.newMessageReq(msgId, data, type));
        return "发送成功";
    }

    /**
     * json消息发布
     */
    @PostMapping("/send/json")
    public String send(@RequestBody JSONObject body) {
        Integer msgId = body.getInteger("msgId");
        JSONObject data = body.getJSONObject("data");
        Integer type = body.getInteger("type");
        tcpClient.getSocketChannel().writeAndFlush(MessageBuilder.newMessageReq(msgId, data.toJSONString(), type));
        return "发送成功";
    }

    /**
     * 连接服务器
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
