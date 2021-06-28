package com.netty.client.controller;

import com.netty.client.netty.NettyClientBootStrap;
import com.netty.client.vo.ProtoMessage;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description :
 *
 * @author : qiDing
 * date: 2021-03-23 14:35
 */
@RestController
@RequestMapping("/client")
@Slf4j
@RequiredArgsConstructor
public class NettyClientController {

    private final NettyClientBootStrap nettyClientBootStrap;

    /**
     * 001：实时路径规划
     * 002：实时任务状态
     * 003：实时位置
     * 004：割草机健康状况
     */
    @RequestMapping("/msgReq")
    public String req(@RequestBody String jsonObject, Integer reqType) throws InterruptedException {
        log.info("/msgReq:{}", jsonObject);
        ProtoMessage.Message.Builder message = ProtoMessage.Message.newBuilder();
        ProtoMessage.MessageRequest.Builder messageRequest = ProtoMessage.MessageRequest.newBuilder();
        messageRequest.setReqType(reqType)
                .setData(jsonObject);
        message.setMessageRequest(messageRequest).setMessageTypeValue(2);
        NettyClientBootStrap.getSocketChannel().writeAndFlush(message.build());
        log.info("发送成功，{}", message.build());
        return "success";
    }

    @RequestMapping("/logReq")
    public String logRequest(String code, String pwd) {
        ProtoMessage.Message.Builder message = ProtoMessage.Message.newBuilder();
        ProtoMessage.LoginRequest.Builder loginRequest = ProtoMessage.LoginRequest.newBuilder();
        // 2  3  ::: 10  10
        message.setLoginRequest(loginRequest.setAccessCode(code).setAccessPwd(pwd).setVersion("v-1.1.0"));
        NettyClientBootStrap.getSocketChannel().writeAndFlush(message.build());
        log.info("发送成功，{}", message.build());
        return "success";
    }

    /**
     * 重连
     */
    @RequestMapping("/rec")
    public String reconnection() throws InterruptedException {
        NettyClientBootStrap.start(new NioEventLoopGroup());
        return "success";
    }

    /**
     * 断开连接
     */
    @RequestMapping("/disc")
    public String disconnect() throws InterruptedException {
        NettyClientBootStrap.close();
        return "success";
    }
}
