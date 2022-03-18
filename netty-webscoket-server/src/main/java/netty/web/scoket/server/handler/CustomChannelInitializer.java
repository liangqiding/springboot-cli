package netty.web.scoket.server.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;
import netty.web.scoket.server.webSocket.SocketChooseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>注册消息处理</p>
 */
@Component("customChannelInitializer")
@Slf4j
public class CustomChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Autowired
    private MessageHandler messageHandler;

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        log.info("Initial message handler.");
        channel.pipeline()
                // todo 协议选择 兼容webSocket协议 ，非webSocket下切换Protobuf，协议已放在 SocketChooseHandler
                .addLast("socketChooseHandler", new SocketChooseHandler())
                .addLast("messageHandler", messageHandler);
    }


}
