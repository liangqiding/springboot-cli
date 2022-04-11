package netty.web.scoket.server.webSocket;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import netty.web.scoket.server.utils.SpringBeanFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * * 协议初始化解码器. *
 * * 用来判定实际使用什么协议.</b> *
 * 多协议切换工具
 *
 * @author : qiDing
 * date: 2021-01-02 10:06
 * @version v1.0.0
 */
@Component
@Slf4j
public class SocketChooseHandler extends ByteToMessageDecoder {

    /**
     * 协议最大长度
     */
    private static final int MAX_LENGTH = 23;

    /**
     * webSocket握手的协议前缀
     */
    private static final String WEB_SOCKET_PREFIX = "GET /";

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        String protocol = getBufStart(in);
        if (protocol.startsWith(WEB_SOCKET_PREFIX)) {
            // 添加 webSocket 协议解码
            log.info("webSocket协议");
            SpringBeanFactory.getBean(WebSocketPipeline.class).webSocketPipelineAdd(ctx);
        } else {
            log.error("***非webSocket协议****");
        }
        in.resetReaderIndex();
        ctx.pipeline().remove(this.getClass());
    }

    private String getBufStart(ByteBuf in) {
        int length = in.readableBytes();
        if (length > MAX_LENGTH) {
            length = MAX_LENGTH;
        }
        // 标记读位置
        in.markReaderIndex();
        byte[] content = new byte[length];
        in.readBytes(content);
        return new String(content);
    }
}
