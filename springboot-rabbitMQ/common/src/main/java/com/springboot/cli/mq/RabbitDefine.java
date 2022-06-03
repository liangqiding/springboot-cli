package com.springboot.cli.mq;

/**
 * 队列定义
 *
 * @author ding
 */
public class RabbitDefine {

    /**
     * 普通交换机
     */
    public final static String DIRECT_EXCHANGE = "direct.exchange";

    /**
     * 普通队列
     */
    public final static String DIRECT_QUEUE = "direct.queue";

    /**
     * 延迟队列交换机
     */
    public final static String DELAY_EXCHANGE = "delay.exchange";

    /**
     * 延迟队列
     */
    public final static String DELAY_QUEUE = "delay.queue";

    /**
     * 通配符交换机
     */
    public final static String FANOUT_EXCHANGE = "fanout.exchange";

    /**
     * 通配符队列
     */
    public final static String FANOUT_QUEUE = "fanout.queue";

    /**
     * topic 交换器
     */
    public final static String TOPIC_EXCHANGE = "topic.exchange";

    /**
     * topic队列1
     */
    public final static String TOPIC_QUEUE_ONE = "topic.queue.one";

    /**
     * topic队列2
     */
    public final static String TOPIC_QUEUE_TWO = "topic.queue.two";

    /**
     * 临时队列交换器
     */
    public final static String TTL_EXCHANGE = "ttl.exchange";

    /**
     * 临时队列
     */
    public final static String TTL_QUEUE = "ttl.queue";

    /**
     * 死信交换机
     */
    public final static String DEAD_EXCHANGE = "dead.exchange";

    /**
     * 死信队列
     */
    public final static String DEAD_QUEUE = "dead.queue";
}
