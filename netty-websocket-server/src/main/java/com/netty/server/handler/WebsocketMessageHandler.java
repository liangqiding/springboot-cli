package com.netty.server.handler;

import com.netty.server.store.WebSocketSession;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Websocket 消息处理器
 *
 * @author qiding
 */
@Slf4j
@Component
public class WebsocketMessageHandler {

    /**
     * 对webSocket 首次握手进行解析
     */
    public void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) {
        // 首次握手进行校验
        this.isFullHttpRequest(ctx, request);
        // 获取请求uri
        String uri = request.uri();
        // 参数分别是 (ws地址,子协议,是否扩展,最大frame长度)
        WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory(getWebSocketLocation(request), null, true, 5 * 1024 * 1024);
        WebSocketServerHandshaker handShaker = factory.newHandshaker(request);
        if (handShaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handShaker.handshake(ctx.channel(), request);
        }
        WebSocketSession.setChannelShaker(ctx.channel().id(), handShaker);
    }

    /**
     * 处理消息
     */
    public void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        // 获取webSocket 会话
        WebSocketServerHandshaker handShaker = WebSocketSession.getChannelShaker(ctx.channel().id());
        // 关闭
        if (frame instanceof CloseWebSocketFrame) {
            log.debug("收到关闭请求");
            handShaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        // 握手PING/PONG
        if (frame instanceof PingWebSocketFrame) {
            ctx.writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // 文本接收和回复
        if (frame instanceof TextWebSocketFrame) {
            log.debug("收到消息：\n{}", ((TextWebSocketFrame) frame).text());
            ctx.writeAndFlush(new TextWebSocketFrame("服务器接收成功！"));
            return;
        }
        // 二进制文本
        if (frame instanceof BinaryWebSocketFrame) {
            ctx.writeAndFlush(frame.retain());
        }
    }

    /**
     * 判断是否是正确的websocket 握手协议
     */
    private void isFullHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) {
        if (!request.decoderResult().isSuccess()) {
            log.error("非webSocket请求");
            this.sendResponse(ctx, request, new DefaultFullHttpResponse(request.protocolVersion(), HttpResponseStatus.BAD_REQUEST, ctx.alloc().buffer()));
            ctx.close();
            return;
        }
        if (!HttpMethod.GET.equals(request.method())) {
            log.error("非GET请求");
            this.sendResponse(ctx, request, new DefaultFullHttpResponse(request.protocolVersion(), HttpResponseStatus.FORBIDDEN, ctx.alloc().buffer()));
            ctx.close();
        }
    }

    /**
     * SSL支持采用wss:
     */
    private String getWebSocketLocation(FullHttpRequest request) {
        return "ws://" + request.headers().get(HttpHeaderNames.HOST) + "/websocket";
    }


    /**
     * http 握手通用响应
     */
    private void sendResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse resp) {
        HttpResponseStatus status = resp.status();
        if (status != HttpResponseStatus.OK) {
            ByteBufUtil.writeUtf8(resp.content(), status.toString());
            HttpUtil.setContentLength(req, resp.content().readableBytes());
        }
        boolean keepAlive = HttpUtil.isKeepAlive(req) && status == HttpResponseStatus.OK;
        HttpUtil.setKeepAlive(req, keepAlive);
        ChannelFuture future = ctx.write(resp);
        if (!keepAlive) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }
}
