package com.netty.client.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netty.client.vo.ProtoMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * description :
 *
 * @author : qiDing
 * date: 2021-03-23 14:34
 */
@Component
@Slf4j
@ChannelHandler.Sharable
public class NettyClientHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("与服务器连接断开...");

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("连接成功执行");
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("抛出异常执行");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object s) throws Exception {
        log.info("收到消息执行：" + s);
        ProtoMessage.Message p = (ProtoMessage.Message) s;
        int type = p.getMessageType().getNumber();
        int type2 = ProtoMessage.MessageType.MESSAGE_REQUEST.getNumber();

        if (type == type2) {
            ProtoMessage.Message.Builder message = ProtoMessage.Message.newBuilder();
            JSONObject jsonObject = JSON.parseObject(p.getMessageRequest().getData());
            String id = jsonObject.getString("id");
            ProtoMessage.MessageResponse.Builder msgResponse = ProtoMessage.MessageResponse.newBuilder();
            msgResponse.setData(jsonObject.toJSONString());
            msgResponse.setStatus(true);
            message.setMessageType(ProtoMessage.MessageType.MESSAGE_RESPONSE);
            message.setMessageResponse(msgResponse);
            channelHandlerContext.channel().writeAndFlush(message.build());
        }

    }
}
