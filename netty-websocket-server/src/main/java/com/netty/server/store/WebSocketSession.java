package com.netty.server.store;

import io.netty.channel.ChannelId;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;

import java.util.HashMap;

/**
 * 管理websocket握手会话
 *
 * @author qiding
 */
public class WebSocketSession {

    private final static HashMap<ChannelId, WebSocketServerHandshaker> CHANNEL_SHAKER = new HashMap<>();

    /**
     * 添加
     */
    public static void setChannelShaker(ChannelId channelId, WebSocketServerHandshaker handShaker) {
        CHANNEL_SHAKER.put(channelId, handShaker);
    }

    /**
     * 获取
     */
    public static WebSocketServerHandshaker getChannelShaker(ChannelId channelId) {
        return CHANNEL_SHAKER.get(channelId);
    }

    /**
     * 释放
     */
    public static void clear(ChannelId channelId) {
        CHANNEL_SHAKER.remove(channelId);
    }
}
