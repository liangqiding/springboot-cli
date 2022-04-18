package com.netty.server.server;

import javax.annotation.PreDestroy;

/**
 * @author qiding
 */
public interface ITcpServer {


    /**
     * 主启动程序，初始化参数
     *
     * @throws Exception 初始化异常
     */
    void start() throws Exception;


    /**
     * 优雅的结束服务器
     *
     * @throws InterruptedException 提前中断异常
     */
    @PreDestroy
    void destroy() throws InterruptedException;
}
