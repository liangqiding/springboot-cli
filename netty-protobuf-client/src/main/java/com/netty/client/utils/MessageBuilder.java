package com.netty.client.utils;


import com.netty.client.procotol.MessageBuf;

/**
 * protobuf 消息构建
 *
 * @author qiding
 */
public class MessageBuilder {

    /**
     * 登录请求
     *
     * @param username 用户名
     * @param password 密码
     */
    public static MessageBuf.Message.Builder newLogin(String username, String password) {
        MessageBuf.LoginRequest.Builder loginMes = MessageBuf.LoginRequest.newBuilder().setUsername(username).setPassword(password);
        return MessageBuf.Message.newBuilder().setLoginRequest(loginMes).setPackType(MessageBuf.Message.PackType.LOGIN_REQ);
    }

    /**
     * 登录响应
     *
     * @param msg  提示消息
     * @param code 错误码
     */
    public static MessageBuf.Message.Builder newLoginResp(String msg, Integer code) {
        MessageBuf.LoginResponse.Builder loginResp = MessageBuf.LoginResponse.newBuilder().setMessage(msg).setCode(code);
        return MessageBuf.Message.newBuilder().setLoginResponse(loginResp).setPackType(MessageBuf.Message.PackType.LOGIN_RESP);
    }

    /**
     * 业务消息请求
     *
     * @param msgId 消息id 请求的id和响应的id需一致，便于判断服务器响应的是哪个业务请求
     * @param data  请求内容
     * @param type  业务类型
     */
    public static MessageBuf.Message.Builder newMessageReq(Integer msgId, String data, Integer type) {
        MessageBuf.MessageRequest.Builder messageReq = MessageBuf.MessageRequest.newBuilder().setMessageId(msgId).setData(data).setType(type);
        return MessageBuf.Message.newBuilder().setMessageRequest(messageReq).setPackType(MessageBuf.Message.PackType.MESSAGE_REQ);
    }

    /**
     * 业务消息响应
     *
     * @param msgId 消息id 请求的id和响应的id需一致，便于判断服务器响应的是哪个业务请求
     * @param msg   提示消息
     * @param code  错误码
     */
    public static MessageBuf.Message.Builder newMessageResp(Integer msgId, String msg, Integer code) {
        MessageBuf.MessageResponse.Builder messageResp = MessageBuf.MessageResponse.newBuilder().setMessageId(msgId).setMessage(msg).setCode(code);
        return MessageBuf.Message.newBuilder().setMessageResponse(messageResp).setPackType(MessageBuf.Message.PackType.MESSAGE_RESP);
    }

}
