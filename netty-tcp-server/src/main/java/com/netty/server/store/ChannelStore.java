package com.netty.server.store;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelOutboundInvoker;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 频道信息存储
 * <p>
 * 封装netty的频道存储，客户端id和频道双向绑定
 *
 * @author qiding
 */
@Slf4j
public class ChannelStore {

    /**
     * 频道绑定 key
     */
    private final static AttributeKey<Object> CLIENT_ID = AttributeKey.valueOf("clientId");

    /**
     * 客户端和频道绑定
     */
    private final static ConcurrentHashMap<String, ChannelId> CLIENT_CHANNEL_MAP = new ConcurrentHashMap<>(16);

    /**
     * 存储频道
     */
    public final static ChannelGroup CHANNEL_GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 重入锁
     */
    private static final Lock LOCK = new ReentrantLock();

    /**
     * 获取单机连接数量
     */
    public static int getLocalConnectCount() {
        return CHANNEL_GROUP.size();
    }

    /**
     * 获取绑定的通道数量（测试用）
     */
    public static int getBindCount() {
        return CLIENT_CHANNEL_MAP.size();
    }

    /**
     * 绑定频道和客户端id
     *
     * @param ctx      连接频道
     * @param clientId 用户id
     */
    public static void bind(ChannelHandlerContext ctx, String clientId) {
        LOCK.lock();
        try {
            // 释放旧的连接
            closeAndClean(clientId);
            // 绑定客户端id到频道上
            ctx.channel().attr(CLIENT_ID).set(clientId);
            // 双向保存客户端id和频道
            CLIENT_CHANNEL_MAP.put(clientId, ctx.channel().id());
            // 保存频道
            CHANNEL_GROUP.add(ctx.channel());
        } finally {
            LOCK.unlock();
        }
    }

    /**
     * 是否已登录
     */
    public static boolean isAuth(ChannelHandlerContext ctx) {
        return !StringUtil.isNullOrEmpty(getClientId(ctx));
    }

    /**
     * 获取客户端id
     *
     * @param ctx 连接频道
     */
    public static String getClientId(ChannelHandlerContext ctx) {
        return ctx.channel().hasAttr(CLIENT_ID) ? (String) ctx.channel().attr(CLIENT_ID).get() : "";
    }

    /**
     * 获取频道
     *
     * @param clientId 客户端id
     */
    public static Channel getChannel(String clientId) {
        return Optional.of(CLIENT_CHANNEL_MAP.containsKey(clientId))
                .filter(Boolean::booleanValue)
                .map(b -> CLIENT_CHANNEL_MAP.get(clientId))
                .map(CHANNEL_GROUP::find)
                .orElse(null);
    }

    /**
     * 释放连接和资源
     * CLIENT_CHANNEL_MAP 需要释放
     * CHANNEL_GROUP 不需要释放，netty会自动帮我们移除
     *
     * @param clientId 客户端id
     */
    public static void closeAndClean(String clientId) {
        // 清除绑定关系
        Optional.of(CLIENT_CHANNEL_MAP.containsKey(clientId))
                .filter(Boolean::booleanValue)
                .ifPresent(oldChannel -> CLIENT_CHANNEL_MAP.remove(clientId));
        // 若存在旧连接，则关闭旧连接，相同clientId，不允许重复连接
        Optional.ofNullable(getChannel(clientId))
                .ifPresent(ChannelOutboundInvoker::close);
    }

    public static void closeAndClean(ChannelHandlerContext ctx) {
        closeAndClean(getClientId(ctx));
    }

}
