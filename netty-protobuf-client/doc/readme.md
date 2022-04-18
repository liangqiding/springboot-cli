# tcp protobuf 测试客户端

> 用于模拟发送protobuf 协议的消息
## 接口
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
————————————————
版权声明：本文为CSDN博主「全栈小定」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/dingdingdandang/article/details/124262130
```