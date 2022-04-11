package netty.web.scoket.server.handler;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import netty.web.scoket.server.webSocket.WebSocketServerHandler;
import org.springframework.stereotype.Component;

/**
 * <p>处理消息请求报文</p>
 * <p>时间：2021-03-04</p>
 */
@Component("messageHandler")
@ChannelHandler.Sharable
@Slf4j
@RequiredArgsConstructor
public class MessageHandler extends SimpleChannelInboundHandler<Object> {

    private final WebSocketServerHandler webSocketServerHandler;

    private static int COUNT_CONNECT = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        Channel channel = channelHandlerContext.channel();
        if (msg instanceof FullHttpRequest) {
            log.info("\n");
            log.info("############### WebSocket-FullHttpRequest 连接总数：{} #####################", COUNT_CONNECT);
            webSocketServerHandler.handleHttpRequest(channel, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            log.info("\n");
            log.info("############### WebSocket-WebSocketFrame #####################");
            webSocketServerHandler.handleWebSocketRequest(channel, (WebSocketFrame) msg);
        } else {
            log.info("\n");
            log.info("############### 其它消息 #####################");
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        COUNT_CONNECT++;
        log.info("客户端连接通知：{},当前总连接数{}", ctx.channel(), COUNT_CONNECT);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端连接超时：{}", ctx.channel());
        COUNT_CONNECT--;
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        COUNT_CONNECT--;
        log.error("服务端异常断开", cause);
    }

}
