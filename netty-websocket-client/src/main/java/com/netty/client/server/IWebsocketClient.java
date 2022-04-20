package com.netty.client.server;

import javax.annotation.PreDestroy;

/**
 * @author qiding
 */
public interface IWebsocketClient {


    /**
     * 主启动程序，初始化参数
     *
     * @throws Exception 初始化异常
     */
    void start() throws Exception;


    /**
     * 重启
     *
     * @throws Exception e
     */
    void reconnect() throws Exception;


    /**
     * 优雅的结束服务器
     *
     * @throws InterruptedException 提前中断异常
     */
    @PreDestroy
    void destroy() throws InterruptedException;
}
