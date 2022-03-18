package netty.web.scoket.server.webSocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketFrameAggregator;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.stereotype.Component;

/**
 * webSocket解码器
 *
 * @author : qiDing
 * date: 2021-01-02 10:13
 * @version v1.0.0
 */
@Component
public class WebSocketPipeline {

    /**
     * netty消息处理类
     */
    private static final String HANDLER_NAME = "messageHandler";

    /**
     * webSocket 独有编码解码器
     */
    public void webSocketPipelineAdd(ChannelHandlerContext ctx) {
        ctx.pipeline()
                // http解码器
                .addBefore(HANDLER_NAME, HttpServerCodec.class.getSimpleName(), new HttpServerCodec())
                // http聚合器
                .addBefore(HANDLER_NAME, ChunkedWriteHandler.class.getSimpleName(), new ChunkedWriteHandler())
                // http消息聚合器
                .addBefore(HANDLER_NAME, HttpObjectAggregator.class.getSimpleName(), new HttpObjectAggregator(1024 * 62))
                // webSocket消息聚合器
                .addBefore(HANDLER_NAME, WebSocketFrameAggregator.class.getSimpleName(), new WebSocketFrameAggregator(1024 * 62));
    }

}
