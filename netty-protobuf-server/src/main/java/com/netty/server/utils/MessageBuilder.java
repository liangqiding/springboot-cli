package com.netty.server.utils;

import com.netty.server.procotol.MessageBuf;

/**
 * protobuf 消息构建
 *
 * @author qiding
 */
public class MessageBuilder {

    /**
     * 登录响应
     *
     * @param msg  提示消息
     * @param code 业务码
     */
    public static MessageBuf.Message.Builder loginResp(String msg, Integer code) {
        MessageBuf.LoginResponse.Builder loginResp = MessageBuf.LoginResponse.newBuilder().setMessage(msg).setCode(code);
        return MessageBuf.Message.newBuilder().setLoginResponse(loginResp).setPackType(MessageBuf.Message.PackType.LOGIN_RESP);
    }

    /**
     * 业务消息响应
     *
     * @param msgId 消息id 请求的id和响应的id需一致，便于判断服务器响应的是哪个业务请求
     * @param msg   提示消息
     * @param code  业务码
     */
    public static MessageBuf.Message.Builder messageResp(Integer msgId, String msg, Integer code) {
        MessageBuf.MessageResponse.Builder messageResp = MessageBuf.MessageResponse.newBuilder().setMessageId(msgId).setMessage(msg).setCode(code);
        return MessageBuf.Message.newBuilder().setMessageResponse(messageResp).setPackType(MessageBuf.Message.PackType.MESSAGE_RESP);
    }

}
