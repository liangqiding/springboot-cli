# 基于netty的 protobuf客户端

## 环境



 | #    | 环境 | 版本           | 说明            |
 | ---- | ---- | -------------- | --------------- |
 | 1    | JDK  | openJdk 11.0.8 | 建议JDK11及以上 |



## 项目结构

```
 ├──channel          管道
 ├──config           服务配置
 ├──controller       http测试api
 ├──handler          消息处理器
 ├──protocol         协议文件
 ├──server           服务配置
 ├──utils            工具包
 ├──NettyClientApplication.java   主启动类
```

## 测试接口

>  基础地址 http://localhost:9999

```json
1. 登录
/login?username=admin&password=123456
2. 发送消息
/send?msgId=1&type=1&data=hello
3. 连接
/connect?ip=192.168.0.99&port=20000
4. 重连
/reconnect
5. 发送json
```json
Request URL:  http://localhost:9999/send/json
Request Method: POST
Request Headers:
{
   "Content-Type":"application/json"
}
Request Body:
{
   "msgId": 1,
   "type": 1,
   "data": {
            "message":"hello"
           }
}
```

